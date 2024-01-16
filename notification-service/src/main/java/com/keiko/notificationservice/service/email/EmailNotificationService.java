package com.keiko.notificationservice.service.email;

import com.keiko.notificationservice.entity.ProductsStockEmail;
import com.keiko.notificationservice.entity.SimpleEmail;

public interface EmailNotificationService {
    void sendEmail (SimpleEmail simpleEmail);

    void sendProductsStock (ProductsStockEmail productsStockEmail);
}
