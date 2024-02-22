package com.keiko.orderservice.service;

import com.keiko.commonservice.entity.resource.payment.CompletedOrder;
import com.keiko.orderservice.entity.resources.PaymentOrder;

public interface PaymentService {
    PaymentOrder createPayment (Long orderId);

    CompletedOrder completePayment (String payId);
}
