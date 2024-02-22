package com.keiko.paymentservice.controller;

import com.keiko.commonservice.entity.resource.payment.CompletedOrder;
import com.keiko.paymentservice.entity.PaymentOrder;
import com.keiko.paymentservice.service.PaypalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

import static com.keiko.paymentservice.constants.WebResourceKeyConstants.*;

@RestController
@RequestMapping (value = PAYPAL_BASE)
public class PaypalController {

    @Autowired
    private PaypalService paypalService;


    @PostMapping (value = INIT_PAYMENT)
    public ResponseEntity<PaymentOrder> createPayment (@RequestParam BigDecimal sum) {
        PaymentOrder paymentOrder = paypalService.createPayment (sum);
        return ResponseEntity.ok (paymentOrder);
    }

    @PostMapping (value = COMPLETE_PAYMENT)
    public ResponseEntity<CompletedOrder> completePayment (@RequestParam String payId) {
        CompletedOrder completedOrder = paypalService.completePayment (payId);
        return ResponseEntity.ok (completedOrder);
    }

    @GetMapping (value = GET_PAYMENT_DETAILS)
    public ResponseEntity<PaymentOrder> getPaymentDetails (@RequestParam String payId) {
        PaymentOrder paymentOrder = paypalService.getOrderDetails (payId);
        return ResponseEntity.ok (paymentOrder);
    }
}
