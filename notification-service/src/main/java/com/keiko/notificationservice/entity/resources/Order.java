package com.keiko.notificationservice.entity.resources;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Order {
    private Shop shop;
    private List<OrderEntry> entries;
    private Address deliveryAddress;
    private Double totalPrice;
    private Double deliveryCost;
    private Double totalAmount;
}
