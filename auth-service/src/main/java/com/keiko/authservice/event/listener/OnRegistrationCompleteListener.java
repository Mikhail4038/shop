package com.keiko.authservice.event.listener;

import com.keiko.authservice.entity.ConfirmRegistrationEmail;
import com.keiko.authservice.entity.VerificationToken;
import com.keiko.authservice.event.OnRegistrationCompleteEvent;
import com.keiko.authservice.service.VerificationTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class OnRegistrationCompleteListener {

    @Autowired
    private VerificationTokenService verificationTokenService;

    @Autowired
    private KafkaTemplate<Long, ConfirmRegistrationEmail> kafkaTemplate;

    @EventListener
    public void onApplicationEvent (OnRegistrationCompleteEvent event) {
        String email = event.getEmail ();
        VerificationToken verificationToken = createVerificationToken (email);
        String token = verificationToken.getToken ();
        sendEmail (email, token);
    }

    private VerificationToken createVerificationToken (String email) {
        final String token = UUID.randomUUID ().toString ();
        VerificationToken verificationToken = new VerificationToken (token, email);
        verificationTokenService.save (verificationToken);
        return verificationToken;
    }

    private void sendEmail (String email, String token) {
        String toAddress = email;
        String subject = "Registration Confirmation";
        String message = String.format (
                "You registered successfully. To confirmRegistrationEmail your registration, please use token: %s", token);

        ConfirmRegistrationEmail confirmRegistrationEmail = ConfirmRegistrationEmail.builder ()
                .toAddress (toAddress)
                .subject (subject)
                .message (message).build ();

        kafkaTemplate.send ("confirmRegistration", confirmRegistrationEmail)
                .whenComplete ((result, ex) -> {
                    if (ex == null) {
                        log.info (String.format (
                                "ConfirmRegistrationEmail registration email to address: %s, sent message broker", confirmRegistrationEmail.getToAddress ()));
                    } else {
                        log.error (String.format (
                                "ConfirmRegistrationEmail registration email to address: %s, didn't send message broker, exception: %s", confirmRegistrationEmail.getToAddress (), ex.getMessage ()));
                    }
                });
    }
}
