package com.keiko.notificationservice.service.email.impl;

import com.keiko.notificationservice.entity.EmailNotificationData;
import com.keiko.notificationservice.properties.EmailProperties;
import com.keiko.notificationservice.service.email.EmailNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class EmailNotificationServiceImpl
        implements EmailNotificationService {

    @Autowired
    public JavaMailSender emailSender;

    @Autowired
    private EmailProperties emailProperties;

    @Override
    public void sendEmail (EmailNotificationData notificationData) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage ();
        simpleMailMessage.setTo (notificationData.getToAddress ());
        simpleMailMessage.setFrom (emailProperties.getSupportEmail ());
        simpleMailMessage.setSubject (notificationData.getSubject ());
        simpleMailMessage.setText (notificationData.getMessage ());
        emailSender.send (simpleMailMessage);
    }

    @Override
    public void sendEmailWithAttachment (EmailNotificationData notificationData, File attachment) {

    }
}
