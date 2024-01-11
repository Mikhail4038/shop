package com.keiko.notificationservice.service.email;

import com.keiko.notificationservice.entity.EmailNotificationData;

import java.io.File;

public interface EmailNotificationService {
    void sendEmail (EmailNotificationData notificationData);

    void sendEmailWithAttachment (EmailNotificationData notificationData, File attachment);
}
