package com.keiko.orderservice.service;

import com.keiko.orderservice.entity.resources.payment.CompletedOrder;
import com.keiko.orderservice.entity.resources.payment.PaymentOrder;

public interface PaymentService {
    PaymentOrder createPayment (Long orderId);

    CompletedOrder completePayment (String payId);
}
