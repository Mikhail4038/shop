package com.keiko.orderservice.service;

import com.keiko.orderservice.entity.DeliveryAddress;
import com.keiko.orderservice.request.ModificationOrderRequest;

public interface OrderService {
    void createOrder (Long userId, Long shopId);

    void saveOrderEntry (ModificationOrderRequest saveOrderEntryRequest);

    void removeOrderEntry (ModificationOrderRequest removeOrderEntryRequest);

    void saveDeliveryAddress (DeliveryAddress deliveryAddress, Long orderId);

    void placeOrder (Long orderId);
}
