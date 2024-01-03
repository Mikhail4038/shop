package com.keiko.authservice.listener;

import com.keiko.authservice.entity.VerificationToken;
import com.keiko.authservice.event.OnRegistrationCompleteEvent;
import com.keiko.authservice.event.listener.RegistrationListener;
import com.keiko.authservice.properties.MailProperties;
import com.keiko.authservice.service.VerificationTokenService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static com.keiko.authservice.util.TestData.registrationCompleteEvent;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith (MockitoExtension.class)
public class RegistrationListenerUnitTest {
    private static final String SUPPORT_EMAIL = "admin@gmail.com";

    @Mock
    private VerificationTokenService verificationTokenService;

    @Mock
    private JavaMailSender javaMailSender;

    @Mock
    private MailProperties mailProperties;

    @InjectMocks
    private RegistrationListener registrationListener;

    @Captor
    ArgumentCaptor<SimpleMailMessage> emailCaptor;
    private static OnRegistrationCompleteEvent event;

    @BeforeAll
    static void setUp () {
        event = registrationCompleteEvent ();
    }

    @Test
    void should_successfully_send_email () {
        when (mailProperties.getSupportEmail ()).thenReturn (SUPPORT_EMAIL);
        registrationListener.onApplicationEvent (event);

        verify (verificationTokenService, times (1)).save (any (VerificationToken.class));
        verify (javaMailSender, times (1)).send (any (SimpleMailMessage.class));
        verify (javaMailSender).send (emailCaptor.capture ());
        SimpleMailMessage mailMessage = emailCaptor.getValue ();

        assertEquals (mailMessage.getTo ()[0], event.getUser ().getEmail ());
        assertEquals (mailMessage.getFrom (), SUPPORT_EMAIL);
    }
}
