package com.keiko.orderservice.controller;

import com.keiko.orderservice.entity.resources.payment.CompletedOrder;
import com.keiko.orderservice.entity.resources.payment.PaymentOrder;
import com.keiko.orderservice.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.keiko.orderservice.constants.WebResourceKeyConstants.*;

@RestController
@RequestMapping (value = PAYPAL_BASE)
public class PaypalController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping (value = INIT_PAYMENT)
    public ResponseEntity<PaymentOrder> createPayment (@RequestParam Long orderId) {
        PaymentOrder paymentOrder = paymentService.createPayment (orderId);
        return ResponseEntity.ok (paymentOrder);
    }

    @PostMapping (value = COMPLETE_PAYMENT)
    public ResponseEntity<CompletedOrder> completePayment (@RequestParam String payId) {
        CompletedOrder completedOrder = paymentService.completePayment (payId);
        return ResponseEntity.ok (completedOrder);
    }
}
