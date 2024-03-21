package com.keiko.orderservice.service.resources;

import com.keiko.commonservice.entity.resource.payment.CompletedOrder;
import com.keiko.orderservice.entity.resources.PaymentOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

import static com.keiko.commonservice.constants.MicroServiceConstants.PAYMENT_SERVICE;
import static com.keiko.commonservice.constants.WebResourceKeyConstants.*;

@Service
@FeignClient (name = PAYMENT_SERVICE)
public interface PaypalService {

    @PostMapping (value = PAYPAL_BASE + CREATE_PAYMENT)
    PaymentOrder createPayment (@RequestParam BigDecimal sum);

    @PostMapping (value = PAYPAL_BASE + COMPLETE_PAYMENT)
    CompletedOrder completePayment (@RequestParam String payId);
}
