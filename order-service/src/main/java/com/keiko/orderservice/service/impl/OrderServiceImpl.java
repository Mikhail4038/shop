package com.keiko.orderservice.service.impl;

import com.keiko.orderservice.dto.model.order.OrderDto;
import com.keiko.orderservice.entity.Order;
import com.keiko.orderservice.entity.OrderEntry;
import com.keiko.orderservice.entity.OrderStatus;
import com.keiko.orderservice.entity.resources.Address;
import com.keiko.orderservice.entity.resources.OrderDetailsEmail;
import com.keiko.orderservice.entity.resources.Shop;
import com.keiko.orderservice.request.RouteDetailsRequest;
import com.keiko.orderservice.request.SellingOrderEntryRequest;
import com.keiko.orderservice.response.RouteDetailsResponse;
import com.keiko.orderservice.service.OrderService;
import com.keiko.orderservice.service.resources.AddressService;
import com.keiko.orderservice.service.resources.NotificationService;
import com.keiko.orderservice.service.resources.ShopService;
import com.keiko.orderservice.service.resources.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void placeOrder (Long orderId) {
        Order order = super.fetchBy (orderId);
        sellProductStocks (order);
        calculateFinalSumOrder (order);
        sendOrderDetailsEmail (order);
        //4.payment
        //5.order status
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
