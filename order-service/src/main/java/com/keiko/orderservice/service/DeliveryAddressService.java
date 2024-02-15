package com.keiko.orderservice.service;

import com.keiko.orderservice.entity.DeliveryAddress;
import com.keiko.orderservice.request.ReverseGeocodeRequest;

public interface DeliveryAddressService {
    void saveDeliveryAddress (DeliveryAddress deliveryAddress, Long orderId);

    void saveDeliveryAddress (ReverseGeocodeRequest reverseGeocodeRequest, Long orderId);

    void saveDeliveryAddress (Long orderId);
}
