package com.keiko.authservice.repository;

import com.keiko.authservice.entity.User;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryIntegrationTest {
    private static final String SAVED_USER_EMAIL = "admin@gmail.com";
    private static final String NOT_SAVED_USER_EMAIL = "UNKNOWN";
    private static final String NOT_CONFIRM_USER_EMAIL = "user@gmail.com";
    private static final String EXCEPTION_MESSAGE = "No value present";
    private static final String VERIFICATION_TOKEN = "token";

    @Autowired
    private UserRepository userRepository;

    @Test
    void whenFindByEmail_thenReturnUser () {
        User user = userRepository.findByEmail (SAVED_USER_EMAIL).get ();
        assertNotNull (user);
        assertEquals (user.getEmail (), SAVED_USER_EMAIL);
    }

    @Test
    void whenFindByNotSavedEmail_thenReturnException () {
        Exception exception = assertThrows (NoSuchElementException.class,
                () -> userRepository.findByEmail (NOT_SAVED_USER_EMAIL).get ());
        assertEquals (exception.getMessage (), EXCEPTION_MESSAGE);
    }

    @Test
    void whenDeleteByEmail_thenDeleteUser () {
        User user = userRepository.findByEmail (SAVED_USER_EMAIL).get ();
        assertNotNull (user);

        userRepository.deleteByEmail (SAVED_USER_EMAIL);
        Exception exception = assertThrows (NoSuchElementException.class,
                () -> userRepository.findByEmail (SAVED_USER_EMAIL).get ());
        assertEquals (exception.getMessage (), EXCEPTION_MESSAGE);
    }

    @Test
    @Transactional
    void whenFindNotConfirmRegistration_thenReturnUsers () {
        List<User> users =
                userRepository.findByEnabledIsAndVerificationToken_expiryDateLessThan (false, LocalDateTime.now ());
        assertTrue (users.size () == 1);
        User user = users.get (0);
        assertEquals (user.getEmail (), NOT_CONFIRM_USER_EMAIL);
    }
}
