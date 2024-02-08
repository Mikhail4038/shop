package com.keiko.orderservice.service;

import com.keiko.orderservice.entity.Address;
import com.keiko.orderservice.request.UpgradeOrderRequest;

public interface OrderService {
    void createOrder (Long userId);

    void saveOrderEntry (UpgradeOrderRequest request);

    void removeOrderEntry (UpgradeOrderRequest request);

    void saveDeliveryAddress (Address deliveryAddress, Long orderId);

    void placeOrder (Long orderId);
}
