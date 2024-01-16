package com.keiko.notificationservice.controller;

import com.keiko.notificationservice.entity.ProductsStockEmail;
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

    @PostMapping (value = PRODUCTS_STOCK)
    public ResponseEntity productsStock (@RequestBody ProductsStockEmail productsStockEmail) {
        emailNotificationService.sendProductsStock (productsStockEmail);
        return ResponseEntity.ok ().build ();
    }
}
