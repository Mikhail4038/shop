package com.keiko.notificationservice.entity.resources;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Order {
    private Long userId;
    private Long shopId;
    private List<OrderEntry> entries;
    private DeliveryAddress deliveryAddress;
    private Double totalPrice;
    private Double deliveryCost;
    private Double totalAmount;
}
