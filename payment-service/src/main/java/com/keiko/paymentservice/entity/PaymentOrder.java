package com.keiko.paymentservice.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PaymentOrder {
    private String status;
    private String payId;
    private String approveUrl;
    private PayerDetails payerDetails;
}

