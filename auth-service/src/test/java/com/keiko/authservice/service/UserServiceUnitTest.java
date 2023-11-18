package com.keiko.authservice.service;

import com.keiko.authservice.entity.User;
import com.keiko.authservice.repository.UserRepository;
import com.keiko.authservice.service.impl.DefaultUserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.keiko.authservice.util.TestData.createTestUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith (MockitoExtension.class)
class UserServiceUnitTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private static UserService userService;
    private static User user;

    @BeforeAll
    static void setUp () {
        userService = new DefaultUserService ();
        user = createTestUser ();
    }

    @Test
    void should_successfully_save () {
        userService.save (user);
        verify (userRepository, times (1)).save (any (User.class));
        verifyNoMoreInteractions (userRepository);
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
    void should_successfully_deleteAll () {
        userService.deleteAll (Arrays.asList (user));
        verify (userRepository, times (1)).deleteAll (anyList ());
        verifyNoMoreInteractions (userRepository);
    }

    @Test
    void should_successfully_findNotConfirmReg () {
        LocalDateTime now = LocalDateTime.now ();
        when (userRepository.findByEnabledIsAndVerificationToken_expiryDateLessThan (false, now))
                .thenReturn (Arrays.asList (user));
        List<User> users = userService.findNotConfirmRegistration (now);
        assertTrue (users.size () == 1);
        assertTrue (users.contains (user));
        verify (userRepository, times (1))
                .findByEnabledIsAndVerificationToken_expiryDateLessThan (anyBoolean (), any (LocalDateTime.class));
        verifyNoMoreInteractions (userRepository);
    }
}
