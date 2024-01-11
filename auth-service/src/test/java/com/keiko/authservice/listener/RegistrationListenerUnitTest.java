package com.keiko.authservice.listener;

import com.keiko.authservice.entity.EmailNotificationData;
import com.keiko.authservice.entity.VerificationToken;
import com.keiko.authservice.event.OnRegistrationCompleteEvent;
import com.keiko.authservice.event.listener.RegistrationListener;
import com.keiko.authservice.service.NotificationService;
import com.keiko.authservice.service.VerificationTokenService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.keiko.authservice.util.TestData.registrationCompleteEvent;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith (MockitoExtension.class)
public class RegistrationListenerUnitTest {

    @Mock
    private VerificationTokenService verificationTokenService;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private RegistrationListener registrationListener;

    @Captor
    ArgumentCaptor<EmailNotificationData> notificationDataArgumentCaptor;
    private static OnRegistrationCompleteEvent event;

    @BeforeAll
    static void setUp () {
        event = registrationCompleteEvent ();
    }

    @Test
    void should_successfully_send_email () {
        registrationListener.onApplicationEvent (event);

        verify (verificationTokenService, times (1)).save (any (VerificationToken.class));
        verify (notificationService, times (1)).sendEmail (notificationDataArgumentCaptor.capture ());

        EmailNotificationData emailNotificationData = notificationDataArgumentCaptor.getValue ();
        assertEquals (emailNotificationData.getToAddress (), event.getUser ().getEmail ());
    }
}
