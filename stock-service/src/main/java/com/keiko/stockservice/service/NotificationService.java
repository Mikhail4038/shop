package com.keiko.stockservice.service;

import com.keiko.stockservice.entity.notification.ProductsStockEmail;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.keiko.stockservice.constants.MicroServiceConstants.NOTIFICATION_SERVICE;
import static com.keiko.stockservice.constants.WebResourceKeyConstants.EMAIL_NOTIFICATION_BASE;
import static com.keiko.stockservice.constants.WebResourceKeyConstants.PRODUCTS_STOCK;

@Service
@FeignClient (name = NOTIFICATION_SERVICE)
public interface NotificationService {

    @PostMapping (value = EMAIL_NOTIFICATION_BASE + PRODUCTS_STOCK)
    void sendProductsStock (@RequestBody ProductsStockEmail productsStockEmail);
}
