package com.keiko.authservice.service;

import com.keiko.authservice.entity.User;
import com.keiko.authservice.entity.VerificationToken;
import com.keiko.authservice.exception.model.*;
import com.keiko.authservice.jwt.JwtProvider;
import com.keiko.authservice.request.LoginRequest;
import com.keiko.authservice.response.LoginResponse;
import com.keiko.authservice.service.impl.DefaultAuthService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static com.keiko.authservice.util.TestData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith (MockitoExtension.class)
class AuthServiceUnitTest {
    private static final String USER_ALREADY_EXIST_EXCEPTION_MESSAGE = "There is an account with that email address: %s";
    private static final String VERIFICATION_TOKEN_PROCESS_EXCEPTION_MESSAGE = "VerificationToken for user: %s,expired";
    private static final String USER_LOCKED_EXCEPTION = "The user's email verification to confirm their account wasn't or was unsuccessful";
    private static final String BAD_CREDENTIAL_EXCEPTION_MESSAGE = "Entered incorrect password: %s";
    private static final String LOGIN_CORRECT_PASSWORD = "500290";
    private static final String LOGIN_INCORRECT_PASSWORD = "25922";

    @Mock
    private UserService userService;

    @Mock
    private VerificationTokenService verificationTokenService;

    @Mock
    private RefreshTokenService refreshTokenService;

    @Mock
    private JwtProvider jwtProvider;

    @InjectMocks
    private static AuthService authService;

    private static User user;
    private static VerificationToken verificationToken;
    private static LoginRequest loginRequest;
    private static LoginResponse loginResponse;

    @BeforeAll
    static void setUp () {
        user = createTestUser ();
        verificationToken = createTestVerificationToken ();
        verificationToken.setUser (user);
        loginRequest = createTestLoginRequest ();
        loginResponse = createTestLoginResponse ();
        authService = new DefaultAuthService ();
    }

    @Test
    void should_successfully_registration () {
        final String email = user.getEmail ();
        final String password = user.getPassword ();

        when (userService.findByEmail (email)).thenThrow (UserNotFoundException.class);

        authService.registration (user);

        verify (userService, times (1)).findByEmail (anyString ());
        verify (userService, times (1)).save (any (User.class));
        InOrder inOrder = inOrder (userService);
        inOrder.verify (userService).findByEmail (anyString ());
        inOrder.verify (userService).save (any (User.class));
    }

    @Test
    void should_unSuccessfully_registration_emailExists () {
        final String email = user.getEmail ();

        when (userService.findByEmail (email)).thenReturn (user);

        Exception exception = assertThrows (UserAlreadyExistException.class,
                () -> authService.registration (user));

        String exceptionMessage = String.format (USER_ALREADY_EXIST_EXCEPTION_MESSAGE, email);
        assertEquals (exception.getMessage (), exceptionMessage);
        verify (userService, times (1)).findByEmail (anyString ());
        verify (userService, times (0)).save (any (User.class));
        verifyNoMoreInteractions (userService);
    }

    @Test
    void should_successfully_confirmRegistration () {
        verificationToken.setExpiryDate (Timestamp.valueOf (LocalDateTime.now ().plusHours (24)));
        final String verificationTokenValue = verificationToken.getToken ();

        when (verificationTokenService.findByToken (verificationTokenValue)).thenReturn (verificationToken);

        authService.confirmRegistration (verificationTokenValue);

        assertTrue (user.isEnabled ());
        verify (userService, times (1)).save (any (User.class));
        verify (verificationTokenService, times (1)).deleteByToken (anyString ());
        verifyNoMoreInteractions (userService);
        verifyNoMoreInteractions (verificationTokenService);
    }

    @Test
    void should_unSuccessfully_confirmRegistration_token_Expired () {
        user.setEnabled (false);
        verificationToken.setExpiryDate (Timestamp.valueOf (LocalDateTime.now ().minusHours (24)));
        final String verificationTokenValue = verificationToken.getToken ();

        when (verificationTokenService.findByToken (verificationTokenValue)).thenReturn (verificationToken);

        Exception exception = assertThrows (VerificationTokenProcessException.class,
                () -> authService.confirmRegistration (verificationTokenValue));

        String exceptionMessage = String.format (VERIFICATION_TOKEN_PROCESS_EXCEPTION_MESSAGE, user);
        assertEquals (exception.getMessage (), exceptionMessage);
        assertFalse (user.isEnabled ());
        verify (userService, times (0)).save (any (User.class));
        verify (verificationTokenService, times (0)).deleteByToken (anyString ());
        verifyNoMoreInteractions (userService);
        verifyNoMoreInteractions (verificationTokenService);
    }

    @Test
    void should_unSuccessfully_login_user_not_enabled () {
        user.setEnabled (false);
        final String email = loginRequest.getEmail ();

        when (userService.findByEmail (email)).thenReturn (user);
        Exception exception = assertThrows (UserLockedException.class,
                () -> authService.login (loginRequest));
        assertEquals (exception.getMessage (), USER_LOCKED_EXCEPTION);
    }

    @Test
    void should_unSuccessfully_login_user_passwords_not_match () {
        final String savedPassword = user.getPassword ();
        user.setEnabled (true);
        loginRequest.setPassword (LOGIN_INCORRECT_PASSWORD);
        final String email = loginRequest.getEmail ();

        when (userService.findByEmail (email)).thenReturn (user);

        Exception exception = assertThrows (BadCredentialException.class,
                () -> authService.login (loginRequest));

        String exceptionMessage = String.format (BAD_CREDENTIAL_EXCEPTION_MESSAGE, LOGIN_INCORRECT_PASSWORD);
        assertEquals (exception.getMessage (), exceptionMessage);
    }

    @Test
    void should_successfully_login () {
        user.setEnabled (true);
        loginRequest.setPassword (LOGIN_CORRECT_PASSWORD);
        verificationToken.setExpiryDate (Timestamp.valueOf (LocalDateTime.now ().plusHours (24)));
        final String presentedEmail = loginRequest.getEmail ();
        final String savedPassword = user.getPassword ();
        final String savedEmail = user.getEmail ();

        when (userService.findByEmail (presentedEmail)).thenReturn (user);
        when (jwtProvider.generateAccessToken (user)).thenReturn (loginResponse.getAccessToken ());
        when (jwtProvider.generateRefreshToken (user)).thenReturn (loginResponse.getRefreshToken ());

        LoginResponse response = authService.login (loginRequest);

        assertEquals (response, loginResponse);
    }
}
