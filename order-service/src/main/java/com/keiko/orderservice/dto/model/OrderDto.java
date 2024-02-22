package com.keiko.orderservice.dto.model;

import com.keiko.commonservice.dto.model.BaseDto;
import com.keiko.commonservice.entity.resource.Address;
import com.keiko.commonservice.entity.resource.Shop;
import com.keiko.commonservice.entity.resource.User;
import com.keiko.orderservice.entity.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderDto extends BaseDto {
    private User user;
    private Shop shop;
    private List<OrderEntryDto> entries;
    private Address deliveryAddress;
    private Double deliveryCost;
    private Double totalPrice;
    private Double totalAmount;
    private OrderStatus orderStatus;
}
