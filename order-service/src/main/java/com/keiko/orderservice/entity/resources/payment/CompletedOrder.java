package com.keiko.orderservice.entity.resources.payment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompletedOrder {
    private String status;
    private String message;
}
