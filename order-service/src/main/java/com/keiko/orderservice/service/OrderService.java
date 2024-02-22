package com.keiko.orderservice.service;

import com.keiko.orderservice.entity.Order;
import com.keiko.orderservice.entity.OrderStatus;

import java.util.List;

public interface OrderService {
    void createOrder (Long userId, Long shopId);

    void cancelOrder (Long orderId);

    void placeOrder (Long orderId);

    Order fetchByPayId (String payId);

    List<Order> fetchByStatus (OrderStatus orderStatus);
}
