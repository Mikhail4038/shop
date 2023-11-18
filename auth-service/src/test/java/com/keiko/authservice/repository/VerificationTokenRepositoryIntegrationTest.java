package com.keiko.authservice.repository;

import com.keiko.authservice.entity.VerificationToken;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class VerificationTokenRepositoryIntegrationTest {
    private static final String SAVED_VERIFICATION_TOKEN = "token";
    private static final String NOT_SAVED_VERIFICATION_TOKEN = "unknown";
    private static final String EXPIRED_VERIFICATION_TOKEN = "value";
    private static final String EXCEPTION_MESSAGE = "No value present";

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Test
    public void whenFindByToken_thenReturnVerificationToken () {
        VerificationToken verificationToken
                = verificationTokenRepository.findByToken (SAVED_VERIFICATION_TOKEN).get ();
        assertNotNull (verificationToken);
        assertEquals (verificationToken.getToken (), SAVED_VERIFICATION_TOKEN);
    }

    @Test
    public void whenFindByNotSavedToken_thenReturnException () {
        Exception exception = assertThrows (NoSuchElementException.class,
                () -> verificationTokenRepository.findByToken (NOT_SAVED_VERIFICATION_TOKEN).get ());
        assertEquals (exception.getMessage (), EXCEPTION_MESSAGE);
    }

    @Test
    public void whenDeleteByToken_thenDeleteVerificationToken () {
        VerificationToken verificationToken
                = verificationTokenRepository.findByToken (SAVED_VERIFICATION_TOKEN).get ();
        assertNotNull (verificationToken);
        verificationTokenRepository.deleteByToken (SAVED_VERIFICATION_TOKEN);
        assertThrows (NoSuchElementException.class,
                () -> verificationTokenRepository.findByToken (SAVED_VERIFICATION_TOKEN).get ());
    }

    @Test
    public void whenDeleteByExpiredToken_thenDeleteVerificationToken () {
        VerificationToken verificationToken
                = verificationTokenRepository.findByToken (EXPIRED_VERIFICATION_TOKEN).get ();
        assertNotNull (verificationToken);
        verificationTokenRepository.deleteByToken (EXPIRED_VERIFICATION_TOKEN);
        assertThrows (NoSuchElementException.class,
                () -> verificationTokenRepository.findByToken (EXPIRED_VERIFICATION_TOKEN).get ());
    }
}
