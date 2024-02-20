package com.keiko.orderservice.entity.resources.payment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentOrder {
    private String status;
    private String payId;
    private String approveUrl;
}
