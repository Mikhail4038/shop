package com.keiko.orderservice.service;

import com.keiko.orderservice.entity.Order;
import com.keiko.orderservice.entity.OrderEntry;
import com.keiko.orderservice.request.AddEntryToOrderRequest;

public interface OrderService {

    void addOrderEntry (AddEntryToOrderRequest request);

    void removeOrderEntry (OrderEntry orderEntry, Order order);

    void placeOrder (Order order);
}
