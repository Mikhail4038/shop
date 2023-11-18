package com.keiko.userservice.repository;

import com.keiko.userservice.entity.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class RoleRepositoryIntegrationTest {

    private static final String SAVED_ROLE_NAME = "ADMIN";
    private static final String NOT_SAVED_ROLE_NAME = "UNKNOWN";
    private static final String EXCEPTION_MESSAGE = "No value present";
    private static final String USER_EMAIL = "admin@gmail.com";

    @Autowired
    private RoleRepository roleRepository;

    @Test
    void whenFindByName_thenReturnRole () {
        Role role = roleRepository.findByName (SAVED_ROLE_NAME).get ();
        assertNotNull (role);
        assertEquals (SAVED_ROLE_NAME, role.getName ());
    }

    @Test
    void whenFindByNotSavedName_thenReturnException () {
        Exception exception = assertThrows (NoSuchElementException.class,
                () -> roleRepository.findByName (NOT_SAVED_ROLE_NAME).get ());
        assertEquals (exception.getMessage (), EXCEPTION_MESSAGE);
    }

    @Test
    void whenFindByUserEmail_thenReturnRoles () {
        List<Role> roles = roleRepository.findByUsers_email (USER_EMAIL);
        assertFalse (roles.isEmpty ());
        assertTrue (roles.size () == 2);
    }
}
