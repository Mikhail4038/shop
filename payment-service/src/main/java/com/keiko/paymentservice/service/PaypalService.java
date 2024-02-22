package com.keiko.paymentservice.service;

import com.keiko.commonservice.entity.resource.payment.CompletedOrder;
import com.keiko.paymentservice.entity.PaymentOrder;

import java.math.BigDecimal;

public interface PaypalService {
    PaymentOrder createPayment (BigDecimal sum);

    CompletedOrder completePayment (String payId);

    PaymentOrder getOrderDetails (String payId);
}
