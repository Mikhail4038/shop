package com.keiko.orderservice.service.impl;

import com.keiko.orderservice.entity.Order;
import com.keiko.orderservice.entity.resources.payment.CompletedOrder;
import com.keiko.orderservice.entity.resources.payment.PaymentOrder;
import com.keiko.orderservice.service.AbstractCrudService;
import com.keiko.orderservice.service.PaymentService;
import com.keiko.orderservice.service.resources.PaypalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PaypalServiceImpl implements PaymentService {

    @Autowired
    private PaypalService paypalService;

    @Autowired
    private AbstractCrudService<Order> orderService;

    @Override
    public PaymentOrder createPayment (Long orderId) {
        Order order = orderService.fetchBy (orderId);
        BigDecimal totalAmount = order.getTotalAmount ();

        PaymentOrder paymentOrder = paypalService.createPayment (totalAmount);
        return paymentOrder;
    }

    @Override
    public CompletedOrder completePayment (String payId) {
        CompletedOrder completedOrder = paypalService.completePayment (payId);
        return completedOrder;
    }
}
