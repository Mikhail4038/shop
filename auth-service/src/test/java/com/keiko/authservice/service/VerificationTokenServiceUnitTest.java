package com.keiko.authservice.service;

import com.keiko.authservice.entity.VerificationToken;
import com.keiko.authservice.repository.VerificationTokenRepository;
import com.keiko.authservice.service.impl.DefaultVerificationTokenService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.keiko.authservice.util.TestData.testVerificationToken;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith (MockitoExtension.class)
class VerificationTokenServiceUnitTest {

    @Mock
    private VerificationTokenRepository verificationTokenRepository;

    @InjectMocks
    private static VerificationTokenService verificationTokenService;
    private static VerificationToken verificationToken;

    @BeforeAll
    static void seUp () {
        verificationToken = testVerificationToken ();
        verificationTokenService = new DefaultVerificationTokenService ();
    }

    @Test
    void should_successfully_save () {
        verificationTokenService.save (verificationToken);
        verify (verificationTokenRepository, times (1)).save (any (VerificationToken.class));
        verifyNoMoreInteractions (verificationTokenRepository);
    }

    @Test
    void should_successfully_findByToken () {
        final String token = verificationToken.getToken ();
        when (verificationTokenRepository.findByToken (token)).thenReturn (Optional.of (verificationToken));
        VerificationToken result = verificationTokenService.findByToken (token);
        assertEquals (result.getToken (), token);
        verify (verificationTokenRepository, times (1)).findByToken (anyString ());
        verifyNoMoreInteractions (verificationTokenRepository);
    }

    @Test
    void should_successfully_deleteByToken () {
        verificationTokenService.deleteByToken (verificationToken.getToken ());
        verify (verificationTokenRepository, times (1)).deleteByToken (anyString ());
        verifyNoMoreInteractions (verificationTokenRepository);
    }

    @Test
    void should_successfully_deleteExpiredToken () {
        final LocalDateTime now = LocalDateTime.now ();
        verificationTokenService.deleteExpiredToken (now);
        verify (verificationTokenRepository, times (1)).deleteByExpiryDateLessThan (any (LocalDateTime.class));
        verifyNoMoreInteractions (verificationTokenRepository);
    }
}
