package com.keiko.orderservice.service;

import com.keiko.commonservice.request.ReverseGeocodeRequest;
import com.keiko.orderservice.entity.DeliveryAddress;

public interface DeliveryAddressService {
    void saveDeliveryAddress (DeliveryAddress deliveryAddress, Long orderId);

    void saveDeliveryAddress (ReverseGeocodeRequest reverseGeocodeRequest, Long orderId);

    void saveDeliveryAddress (Long orderId);
}
