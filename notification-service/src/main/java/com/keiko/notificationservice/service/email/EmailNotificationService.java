package com.keiko.notificationservice.service.email;

import com.keiko.notificationservice.entity.OrderDetailsEmail;
import com.keiko.notificationservice.entity.ProductStocksEmail;
import com.keiko.notificationservice.entity.SimpleEmail;

public interface EmailNotificationService {
    void sendEmail (SimpleEmail simpleEmail);

    void sendProductsStock (ProductStocksEmail productStocksEmail);

    void sendOrderDetails (OrderDetailsEmail orderDetailsEmail);
}
