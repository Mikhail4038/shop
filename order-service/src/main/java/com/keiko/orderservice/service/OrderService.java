package com.keiko.orderservice.service;

import com.keiko.orderservice.entity.Order;
import com.keiko.orderservice.entity.OrderEntry;
import com.keiko.orderservice.request.UpgradeOrderRequest;

public interface OrderService {

    void saveOrderEntry (UpgradeOrderRequest request);

    void removeOrderEntry (UpgradeOrderRequest request);

    void placeOrder (Order order);
}
