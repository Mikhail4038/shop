package com.keiko.userservice.service;

import com.keiko.userservice.entity.Role;
import com.keiko.userservice.entity.User;
import com.keiko.userservice.repository.UserRepository;
import com.keiko.userservice.service.impl.DefaultUserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.keiko.userservice.util.TestData.createTestRole;
import static com.keiko.userservice.util.TestData.createTestUser;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith (MockitoExtension.class)
class UserServiceUnitTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private static UserService userService;

    private static User user;
    private static Role role;

    @BeforeAll
    static void setUp () {
        userService = new DefaultUserService ();
        user = createTestUser ();
        role = createTestRole ();
        user.setRoles (Set.of (role));
    }

    @Test
    void should_successfully_findByEmail () {
        final String email = user.getEmail ();
        when (userRepository.findByEmail (email)).thenReturn (Optional.of (user));
        User result = userService.findByEmail (email);
        assertEquals (result, user);
        verify (userRepository, times (1)).findByEmail (anyString ());
        verifyNoMoreInteractions (userRepository);
    }

    @Test
    void should_successfully_deleteByEmail () {
        userService.deleteByEmail (user.getEmail ());
        verify (userRepository, times (1)).deleteByEmail (anyString ());
        verifyNoMoreInteractions (userRepository);
    }

    @Test
    void should_successfully_findUsersByRoleName () {
        final String name = role.getName ();
        when (userRepository.findByRoles_name (name)).thenReturn (Arrays.asList (user));
        List<User> users = userService.findByRole (name);
        assertFalse (users.isEmpty ());
        assertTrue (users.size () == 1);
        assertTrue (users.contains (user));
        verify (userRepository, times (1)).findByRoles_name (anyString ());
        verifyNoMoreInteractions (userRepository);
    }
}
