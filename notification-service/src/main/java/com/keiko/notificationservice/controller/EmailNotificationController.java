package com.keiko.notificationservice.controller;

import com.keiko.notificationservice.entity.EmailNotificationData;
import com.keiko.notificationservice.service.email.EmailNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.keiko.notificationservice.constants.WebResourceKeyConstants.EMAIL_NOTIFICATION_BASE;
import static com.keiko.notificationservice.constants.WebResourceKeyConstants.SIMPLE_EMAIL;

@RestController
@RequestMapping (value = EMAIL_NOTIFICATION_BASE)
public class EmailNotificationController {

    @Autowired
    private EmailNotificationService emailNotificationService;

    @PostMapping (value = SIMPLE_EMAIL)
    public ResponseEntity sendEmail (@RequestBody EmailNotificationData emailNotificationData) {
        emailNotificationService.sendEmail (emailNotificationData);
        return ResponseEntity.ok ().build ();
    }
}
