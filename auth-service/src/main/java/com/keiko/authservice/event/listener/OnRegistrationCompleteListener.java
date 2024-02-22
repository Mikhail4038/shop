package com.keiko.authservice.event.listener;

import com.keiko.authservice.entity.EmailNotificationData;
import com.keiko.authservice.entity.User;
import com.keiko.authservice.entity.VerificationToken;
import com.keiko.authservice.event.OnRegistrationCompleteEvent;
import com.keiko.authservice.service.resources.NotificationService;
import com.keiko.authservice.service.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class OnRegistrationCompleteListener {

    @Autowired
    private VerificationTokenService verificationTokenService;

    @Autowired
    private NotificationService notificationService;

    @EventListener
    public void onApplicationEvent (OnRegistrationCompleteEvent event) {
        User user = event.getUser ();

        VerificationToken verificationToken = createVerificationToken (user);

        String userEmail = user.getEmail ();
        String token = verificationToken.getToken ();

        sendEmail (userEmail, token);
    }

    private VerificationToken createVerificationToken (User user) {
        final String token = UUID.randomUUID ().toString ();
        VerificationToken verificationToken = new VerificationToken (token, user);
        verificationTokenService.save (verificationToken);
        return verificationToken;
    }

    private void sendEmail (String userEmail, String token) {
        String toAddress = userEmail;
        String subject = "Registration Confirmation";
        String message = String.format (
                "You registered successfully. To confirm your registration, please use token: %s", token);

        EmailNotificationData data = EmailNotificationData.builder ()
                .toAddress (toAddress)
                .subject (subject)
                .message (message).build ();

        notificationService.sendEmail (data);
    }
}
