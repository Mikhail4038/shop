package com.keiko.orderservice.service.impl;

import com.keiko.orderservice.entity.Order;
import com.keiko.orderservice.entity.OrderStatus;
import com.keiko.orderservice.entity.resources.payment.CompletedOrder;
import com.keiko.orderservice.entity.resources.payment.PaymentOrder;
import com.keiko.orderservice.exception.model.OrderProcessException;
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
    private OrderServiceImpl orderService;

    @Override
    public PaymentOrder createPayment (Long orderId) {
        Order order = orderService.fetchBy (orderId);
        checkOrderStatus (order);
        BigDecimal totalAmount = order.getTotalAmount ();

        PaymentOrder paymentOrder = paypalService.createPayment (totalAmount);

        setPayIdAndOrderStatus (order, paymentOrder);
        return paymentOrder;
    }

    @Override
    public CompletedOrder completePayment (String payId) {
        CompletedOrder completedOrder = paypalService.completePayment (payId);
        changeOrderStatus (payId);
        return completedOrder;
    }

    private void setPayIdAndOrderStatus (Order order, PaymentOrder paymentOrder) {
        String payId = paymentOrder.getPayId ();
        order.setPayId (payId);
        order.setOrderStatus (OrderStatus.PAYMENT_CAPTURED);
        orderService.save (order);
    }

    private void changeOrderStatus (String payId) {
        Order order = orderService.fetchByPayId (payId);
        order.setOrderStatus (OrderStatus.PAID);
        orderService.save (order);
    }

    private void checkOrderStatus (Order order) {
        OrderStatus presentedOrderStatus = order.getOrderStatus ();
        if (!presentedOrderStatus.equals (OrderStatus.PLACED)) {
            throw new OrderProcessException ("Order cannot be paid, please check order status");
        }
    }
}
