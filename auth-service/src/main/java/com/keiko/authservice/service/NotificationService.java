package com.keiko.authservice.service;

import com.keiko.authservice.entity.EmailNotificationData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.keiko.authservice.constants.MicroServiceConstants.NOTIFICATION_SERVICE;
import static com.keiko.authservice.constants.WebResourceKeyConstants.EMAIL_NOTIFICATION_BASE;
import static com.keiko.authservice.constants.WebResourceKeyConstants.SIMPLE_EMAIL;

@Service
@FeignClient (name = NOTIFICATION_SERVICE)
public interface NotificationService {

    @PostMapping (value = EMAIL_NOTIFICATION_BASE + SIMPLE_EMAIL)
    void sendEmail (@RequestBody EmailNotificationData emailNotificationData);
}
