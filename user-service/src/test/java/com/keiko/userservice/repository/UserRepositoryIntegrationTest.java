package com.keiko.userservice.repository;

import com.keiko.userservice.entity.User;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryIntegrationTest {
    private static final String SAVED_USER_EMAIL = "admin@gmail.com";
    private static final String NOT_SAVED_USER_EMAIL = "unknown@gmail.com";
    private static final String INCORRECT_USER_EMAIL = "unknowngmail.com";
    private static final String USER_PASSWORD = "500290";
    private static final String EXCEPTION_MESSAGE = "No value present";
    private static final String ROLE_NAME = "SUPER_TEST";

    @Autowired
    private UserRepository userRepository;

    @Test
    void whenSaveUserWithIncorrectEmail_thenThrowException () {
        User user = new User ();
        user.setPassword (USER_PASSWORD);
        user.setEmail (INCORRECT_USER_EMAIL);
        assertThrows (ConstraintViolationException.class, () -> userRepository.save (user));
    }

    @Test
    void whenFindByEmail_thenReturnUser () {
        User user = userRepository.findByEmail (SAVED_USER_EMAIL).get ();
        assertNotNull (user);
        assertEquals (SAVED_USER_EMAIL, user.getEmail ());
    }

    @Test
    void whenFindByNotSavedEmail_thenReturnException () {
        Exception exception = assertThrows (NoSuchElementException.class,
                () -> userRepository.findByEmail (NOT_SAVED_USER_EMAIL).get ());
        assertEquals (exception.getMessage (), EXCEPTION_MESSAGE);
    }

    @Test
    void whenFindByRoleName_thenReturnUsers () {
        List<User> users = userRepository.findByRoles_name (ROLE_NAME);
        assertFalse (users.isEmpty ());
        assertTrue (users.size () == 2);
    }

    @Test
    void whenDeleteByEmail_thenDeleteUser () {
        User user = userRepository.findByEmail (SAVED_USER_EMAIL).get ();
        assertNotNull (user);
        userRepository.deleteByEmail (SAVED_USER_EMAIL);
        assertThrows (NoSuchElementException.class,
                () -> userRepository.findByEmail (SAVED_USER_EMAIL).get ());
    }
}
