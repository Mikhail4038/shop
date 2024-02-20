package com.keiko.orderservice.service.impl;

import com.keiko.orderservice.dto.model.order.OrderDto;
import com.keiko.orderservice.entity.Order;
import com.keiko.orderservice.entity.OrderEntry;
import com.keiko.orderservice.entity.OrderStatus;
import com.keiko.orderservice.entity.resources.Address;
import com.keiko.orderservice.entity.resources.OrderDetailsEmail;
import com.keiko.orderservice.entity.resources.Shop;
import com.keiko.orderservice.exception.model.OrderProcessException;
import com.keiko.orderservice.repository.OrderRepository;
import com.keiko.orderservice.request.BookingOrderEntryRequest;
import com.keiko.orderservice.request.RouteDetailsRequest;
import com.keiko.orderservice.request.SellingOrderEntryRequest;
import com.keiko.orderservice.response.RouteDetailsResponse;
import com.keiko.orderservice.service.OrderService;
import com.keiko.orderservice.service.resources.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Function;

@Service
public class OrderServiceImpl extends AbstractCrudServiceImpl<Order>
        implements OrderService {

    private static final Integer KILOMETER_BASE_RATE = 3;

    @Autowired
    private ShopService shopService;

    @Autowired
    private UserService userService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private PaypalService paymentService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private Function<Order, OrderDto> toDtoConverter;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public void createOrder (Long userId, Long shopId) {
        Order order = new Order ();
        order.setUserId (userId);
        order.setShopId (shopId);
        order.setOrderStatus (OrderStatus.CREATED);
        super.save (order);
    }

    @Override
    public void cancelOrder (Long orderId) {
        Order order = super.fetchBy (orderId);
        if (!isValidOrderStatus (order, OrderStatus.CREATED) ||
                !isValidOrderStatus (order, OrderStatus.PLACED)) {
            throw new OrderProcessException ("Order cannot be cancel, please check order status");
        }

        for (OrderEntry entry : order.getEntries ()) {
            BookingOrderEntryRequest cancelBookingRequest = BookingOrderEntryRequest.builder ()
                    .ean (entry.getProductEan ())
                    .quantity (entry.getQuantity ())
                    .shopId (order.getShopId ())
                    .build ();
            shopService.cancelBookedStock (cancelBookingRequest);

        }
        order.setOrderStatus (OrderStatus.CANCELLED);
        super.save (order);
    }

    @Override
    @Transactional
    public void placeOrder (Long orderId) {
        Order order = super.fetchBy (orderId);
        if (!isValidOrderStatus (order, OrderStatus.CREATED)) {
            throw new OrderProcessException ("Order cannot be placed, please check order status");
        }
        ;
        sellProductStocks (order);
        calculateFinalSumOrder (order);
        sendOrderDetailsEmail (order);
        order.setOrderStatus (OrderStatus.PLACED);
        super.save (order);
    }

    @Override
    public Order fetchByPayId (String payId) {
        return orderRepository.findByPayId (payId).orElseThrow ();
    }

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
        Double deliveryCost = deliveryDistance * KILOMETER_BASE_RATE;
        order.setDeliveryCost (BigDecimal.valueOf (deliveryCost));
    }

    private void calculateTotalAmount (Order order) {
        BigDecimal totalPrice = order.getTotalPrice ();
        BigDecimal deliveryCost = order.getDeliveryCost ();
        order.setTotalAmount (totalPrice.add (deliveryCost));
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

    private boolean isValidOrderStatus (Order order, OrderStatus status) {
        OrderStatus orderStatus = order.getOrderStatus ();
        return orderStatus.equals (status);
    }
}
