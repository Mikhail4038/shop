package com.keiko.orderservice.service;

public interface OrderService {
    void createOrder (Long userId, Long shopId);

    void placeOrder (Long orderId);
}
