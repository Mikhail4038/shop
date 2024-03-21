package com.keiko.orderservice.controller;

import com.keiko.commonservice.entity.resource.payment.CompletedOrder;
import com.keiko.orderservice.entity.resources.PaymentOrder;
import com.keiko.orderservice.service.PaymentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.keiko.commonservice.constants.WebResourceKeyConstants.*;

@RestController
@RequestMapping (value = PAYPAL_BASE)
@Tag (name = "Paypal API")
public class PaypalController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping (value = CREATE_PAYMENT)
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
