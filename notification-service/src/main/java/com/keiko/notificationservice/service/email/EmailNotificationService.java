package com.keiko.notificationservice.service.email;

import com.keiko.notificationservice.entity.productStocksEmail;
import com.keiko.notificationservice.entity.SimpleEmail;

public interface EmailNotificationService {
    void sendEmail (SimpleEmail simpleEmail);

    void sendProductsStock (productStocksEmail productStocksEmail);
}
