package com.keiko.notificationservice.controller;

import com.keiko.notificationservice.entity.OrderDetailsEmail;
import com.keiko.notificationservice.entity.ProductStocksEmail;
import com.keiko.notificationservice.entity.SimpleEmail;
import com.keiko.notificationservice.service.email.EmailNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.keiko.notificationservice.constants.WebResourceKeyConstants.*;

@RestController
@RequestMapping (value = EMAIL_NOTIFICATION_BASE)
public class EmailNotificationController {

    @Autowired
    private EmailNotificationService emailNotificationService;

    @PostMapping (value = SIMPLE)
    public ResponseEntity simple (@RequestBody SimpleEmail simpleEmail) {
        emailNotificationService.sendEmail (simpleEmail);
        return ResponseEntity.ok ().build ();
    }

    @PostMapping (value = PRODUCT_STOCKS)
    public ResponseEntity productStocks (@RequestBody ProductStocksEmail productStocksEmail) {
        emailNotificationService.sendProductsStock (productStocksEmail);
        return ResponseEntity.ok ().build ();
    }

    @PostMapping (value = ORDER_DETAILS)
    public ResponseEntity orderDetails (@RequestBody OrderDetailsEmail orderDetailsEmail) {
        emailNotificationService.sendOrderDetails (orderDetailsEmail);
        return ResponseEntity.ok ().build ();
    }
}
