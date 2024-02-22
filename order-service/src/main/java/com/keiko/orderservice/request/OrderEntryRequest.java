package com.keiko.orderservice.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderEntryRequest {
    private String productEan;
    private Long quantity;
    private Long orderId;
}
