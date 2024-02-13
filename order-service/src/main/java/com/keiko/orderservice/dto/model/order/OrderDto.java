package com.keiko.orderservice.dto.model.order;

import com.keiko.orderservice.dto.model.BaseDto;
import com.keiko.orderservice.entity.DeliveryAddress;
import com.keiko.orderservice.entity.OrderStatus;
import com.keiko.orderservice.entity.resources.Shop;
import com.keiko.orderservice.entity.resources.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderDto extends BaseDto {
    private User user;
    private Shop shop;
    private List<OrderEntryDto> entries;
    private DeliveryAddress deliveryAddress;
    private Double deliveryCost;
    private Double totalPrice;
    private Double totalAmount;
    private OrderStatus orderStatus;
}
