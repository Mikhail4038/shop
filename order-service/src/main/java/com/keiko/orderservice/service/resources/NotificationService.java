package com.keiko.orderservice.service.resources;

import com.keiko.orderservice.entity.resources.OrderDetailsEmail;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.keiko.orderservice.constants.MicroServiceConstants.NOTIFICATION_SERVICE;
import static com.keiko.orderservice.constants.WebResourceKeyConstants.EMAIL_NOTIFICATION_BASE;
import static com.keiko.orderservice.constants.WebResourceKeyConstants.ORDER_DETAILS;

@Service
@FeignClient (name = NOTIFICATION_SERVICE)
public interface NotificationService {

    @PostMapping (value = EMAIL_NOTIFICATION_BASE + ORDER_DETAILS)
    void sendOrderDetails (@RequestBody OrderDetailsEmail orderDetailsEmail);
}
