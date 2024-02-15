package com.keiko.orderservice.service.impl;

import com.keiko.orderservice.dto.model.order.OrderDto;
import com.keiko.orderservice.entity.DeliveryAddress;
import com.keiko.orderservice.entity.Order;
import com.keiko.orderservice.entity.OrderEntry;
import com.keiko.orderservice.entity.OrderStatus;
import com.keiko.orderservice.entity.resources.Address;
import com.keiko.orderservice.entity.resources.OrderDetailsEmail;
import com.keiko.orderservice.entity.resources.Shop;
import com.keiko.orderservice.entity.resources.User;
import com.keiko.orderservice.event.RecalculateOrderEvent;
import com.keiko.orderservice.request.*;
import com.keiko.orderservice.response.RouteDetailsResponse;
import com.keiko.orderservice.service.OrderService;
import com.keiko.orderservice.service.resources.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static java.util.Objects.isNull;

@Service
public class OrderServiceImpl extends AbstractCrudServiceImpl<Order>
        implements OrderService {

    private static final Integer KILOMETER_BASE_RATE = 3;

    @Autowired
    private ShopService shopService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private Function<Order, OrderDto> toDtoConverter;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Override
    public void createOrder (Long userId, Long shopId) {
        Order order = new Order ();
        order.setUserId (userId);
        order.setShopId (shopId);
        order.setOrderStatus (OrderStatus.CREATED);
        super.save (order);
    }

    @Override
    @Transactional
    public void saveOrderEntry (ModificationOrderRequest saveOrderEntryRequest) {
        String ean = saveOrderEntryRequest.getProductEan ();
        Long qty = saveOrderEntryRequest.getQuantity ();
        Long orderId = saveOrderEntryRequest.getOrderId ();

        Order order = super.fetchBy (orderId);
        Long shopId = order.getShopId ();

        qty = beforeSaveOrderEntry (ean, shopId, qty);
        OrderEntry orderEntry = createOrGetOrderEntry (ean, qty, order);
        saveOrderEntry (orderEntry, order);
        afterSaveOrderEntry (buildBookedRequest (ean, qty, shopId));
    }

    @Override
    @Transactional
    public void removeOrderEntry (ModificationOrderRequest removeOrderEntryRequest) {
        String ean = removeOrderEntryRequest.getProductEan ();
        Long qty = removeOrderEntryRequest.getQuantity ();
        Long orderId = removeOrderEntryRequest.getOrderId ();

        Order order = super.fetchBy (orderId);
        Long shopId = order.getShopId ();

        OrderEntry orderEntry = getOrderEntry (ean, order);
        removeOrChangeQtyOrderEntry (qty, orderEntry, order);
        afterRemoveOrderEntry (buildBookedRequest (ean, qty, shopId));
    }

    @Override
    public void saveDeliveryAddress (DeliveryAddress deliveryAddress, Long orderId) {
        Order order = super.fetchBy (orderId);
        order.setDeliveryAddress (deliveryAddress);
        super.save (order);
    }

    @Override
    public void saveDeliveryAddress (ReverseGeocodeRequest reverseGeocodeRequest, Long orderId) {
        DeliveryAddress deliveryAddress = addressService.reverseGeocode (reverseGeocodeRequest);
        this.saveDeliveryAddress (deliveryAddress, orderId);


    }

    @Override
    public void saveDeliveryAddress (Long orderId) {
        Order order = this.fetchBy (orderId);
        Long userId = order.getUserId ();
        User user = userService.fetchBy (userId);
        DeliveryAddress deliveryAddress = modelMapper.map (user.getUserAddress (), DeliveryAddress.class);
        this.saveDeliveryAddress (deliveryAddress, orderId);
    }

    @Override
    @Transactional
    public void placeOrder (Long orderId) {
        Order order = super.fetchBy (orderId);
        sellProductStocks (order);
        calculateFinalSumOrder (order);
        sendOrderDetailsEmail (order);
        //4.payment
        //5.order status
    }

    //save
    private OrderEntry createOrGetOrderEntry (String ean, Long qty, Order order) {
        Optional<OrderEntry> entry = order.getEntries ()
                .stream ()
                .filter (e -> e.getProductEan ().equals (ean))
                .findFirst ();

        if (entry.isPresent ()) {
            OrderEntry orderEntry = entry.get ();
            Long quantity = orderEntry.getQuantity ();
            quantity += qty;
            orderEntry.setQuantity (quantity);
            return orderEntry;
        } else {
            return OrderEntry.builder ()
                    .productEan (ean)
                    .quantity (qty)
                    .build ();
        }
    }

    private Long beforeSaveOrderEntry (String ean, Long shopId, Long qty) {
        Long availableForSell = shopService.countProductForSell (ean, shopId);
        return qty > availableForSell ? availableForSell : qty;
    }

    private void saveOrderEntry (OrderEntry orderEntry, Order order) {
        if (isNull (orderEntry.getId ())) {
            order.getEntries ().add (orderEntry);
        }
        eventPublisher.publishEvent (new RecalculateOrderEvent (order));
        super.save (order);
    }

    private void afterSaveOrderEntry (BookingOrderEntryRequest bookedRequest) {
        shopService.bookedStock (bookedRequest);
    }

    private BookingOrderEntryRequest buildBookedRequest (String ean, Long qty, Long shopId) {
        return BookingOrderEntryRequest.builder ()
                .ean (ean)
                .quantity (qty)
                .shopId (shopId)
                .build ();
    }

    //remove
    private OrderEntry getOrderEntry (String ean, Order order) {
        List<OrderEntry> entries = order.getEntries ();
        OrderEntry orderEntry = entries
                .stream ()
                .filter (entry -> entry.getProductEan ().equals (ean))
                .findFirst ().get ();
        return orderEntry;
    }

    private void removeOrChangeQtyOrderEntry (Long qty, OrderEntry orderEntry, Order order) {
        List<OrderEntry> entries = order.getEntries ();
        Long quantity = orderEntry.getQuantity ();
        quantity -= qty;

        if (quantity > 0) {
            orderEntry.setQuantity (quantity);
        } else {
            entries.remove (orderEntry);
        }
        eventPublisher.publishEvent (new RecalculateOrderEvent (order));
        super.save (order);
    }

    private void afterRemoveOrderEntry (BookingOrderEntryRequest cancelBookedRequest) {
        shopService.cancelBookedStock (cancelBookedRequest);
    }

    //place
    private void sellProductStocks (Order order) {
        List<OrderEntry> entries = order.getEntries ();
        Long shopId = order.getShopId ();
        SellingOrderEntryRequest sellingRequest =
                SellingOrderEntryRequest.builder ()
                        .entries (entries)
                        .shopId (shopId)
                        .build ();
        shopService.sellProductStocks (sellingRequest);
    }

    private void calculateFinalSumOrder (Order order) {
        calculateDeliveryCost (order);
        calculateTotalAmount (order);
    }

    private void calculateDeliveryCost (Order order) {
        Shop shop = shopService.fetchBy (order.getShopId ());
        Address shopAddress = shop.getShopAddress ();
        Address deliveryAddress = modelMapper.map (order.getDeliveryAddress (), Address.class);

        RouteDetailsRequest routeDetailsRequest = RouteDetailsRequest
                .builder ()
                .from (shopAddress)
                .to (deliveryAddress)
                .build ();

        RouteDetailsResponse routeDetailsResponse = addressService.calculateRoute (routeDetailsRequest);
        Double deliveryDistance = routeDetailsResponse.getDistance ();
        order.setDeliveryCost (deliveryDistance * KILOMETER_BASE_RATE);
    }

    private void calculateTotalAmount (Order order) {
        Double totalPrice = order.getTotalPrice ();
        Double deliveryCost = order.getDeliveryCost ();
        order.setTotalAmount (totalPrice + deliveryCost);
    }


    private void sendOrderDetailsEmail (Order order) {
        String email = userService.fetchBy (order.getUserId ()).getEmail ();
        OrderDto dto = toDtoConverter.apply (order);
        OrderDetailsEmail orderDetailsEmail = OrderDetailsEmail.builder ()
                .toAddress (email)
                .subject ("Thank you for placing the order")
                .message ("Order details: ")
                .order (dto)
                .build ();
        notificationService.sendOrderDetails (orderDetailsEmail);
    }
}
