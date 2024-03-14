package com.keiko.authservice.listener;

import com.keiko.authservice.entity.ConfirmRegistrationEmail;
import com.keiko.authservice.entity.VerificationToken;
import com.keiko.authservice.event.OnRegistrationCompleteEvent;
import com.keiko.authservice.event.listener.OnRegistrationCompleteListener;
import com.keiko.authservice.service.VerificationTokenService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.concurrent.CompletableFuture;

import static com.keiko.authservice.util.TestData.registrationCompleteEvent;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith (MockitoExtension.class)
public class OnRegistrationCompleteListenerUnitTest {

    @Mock
    private VerificationTokenService verificationTokenService;

    @Mock
    private KafkaTemplate<Long, ConfirmRegistrationEmail> kafkaTemplate;

    @Mock
    private CompletableFuture completableFuture;

    @InjectMocks
    private OnRegistrationCompleteListener onRegistrationCompleteListener;

    private static OnRegistrationCompleteEvent event;

    @BeforeAll
    static void setUp () {
        event = registrationCompleteEvent ();
    }

    @Test
    void should_successfully_send_email () {
        when (kafkaTemplate.send (any (), any ())).thenReturn (completableFuture);

        onRegistrationCompleteListener.onApplicationEvent (event);

        verify (verificationTokenService, times (1)).save (any (VerificationToken.class));
        verify (kafkaTemplate, times (1)).send (any (), any ());
    }
}
