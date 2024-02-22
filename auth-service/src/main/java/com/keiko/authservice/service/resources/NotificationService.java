package com.keiko.authservice.service.resources;

import com.keiko.authservice.entity.EmailNotificationData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.keiko.authservice.constants.WebResourceKeyConstants.SIMPLE;
import static com.keiko.commonservice.constants.MicroServiceConstants.NOTIFICATION_SERVICE;
import static com.keiko.commonservice.constants.WebResourceKeyConstants.EMAIL_NOTIFICATION_BASE;

@Service
@FeignClient (name = NOTIFICATION_SERVICE)
public interface NotificationService {

    @PostMapping (value = EMAIL_NOTIFICATION_BASE + SIMPLE)
    void sendEmail (@RequestBody EmailNotificationData emailNotificationData);
}
