package com.keiko.orderservice.service;

import com.keiko.orderservice.entity.DeliveryAddress;
import com.keiko.orderservice.request.ModificationOrderRequest;
import com.keiko.orderservice.request.ReverseGeocodeRequest;

public interface OrderService {
    void createOrder (Long userId, Long shopId);

    void saveOrderEntry (ModificationOrderRequest saveOrderEntryRequest);

    void removeOrderEntry (ModificationOrderRequest removeOrderEntryRequest);

    void saveDeliveryAddress (DeliveryAddress deliveryAddress, Long orderId);

    void saveDeliveryAddress (ReverseGeocodeRequest reverseGeocodeRequest, Long orderId);

    void saveDeliveryAddress (Long orderId);

    void placeOrder (Long orderId);
}
