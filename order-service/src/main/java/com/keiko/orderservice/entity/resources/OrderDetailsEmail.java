package com.keiko.orderservice.entity.resources;

import com.keiko.orderservice.dto.model.order.OrderDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OrderDetailsEmail {
    private String toAddress;
    private String subject;
    private String message;
    private OrderDto order;
}
