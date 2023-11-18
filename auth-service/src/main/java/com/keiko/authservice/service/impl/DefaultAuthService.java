package com.keiko.authservice.service.impl;

import com.keiko.authservice.entity.User;
import com.keiko.authservice.entity.VerificationToken;
import com.keiko.authservice.exception.model.*;
import com.keiko.authservice.jwt.JwtProvider;
import com.keiko.authservice.request.LoginRequest;
import com.keiko.authservice.response.LoginResponse;
import com.keiko.authservice.service.AuthService;
import com.keiko.authservice.service.RefreshTokenService;
import com.keiko.authservice.service.UserService;
import com.keiko.authservice.service.VerificationTokenService;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;

@Service
@Validated
public class DefaultAuthService implements AuthService {

    @Autowired
    private UserService userService;

    @Autowired
    private VerificationTokenService verificationTokenService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private JwtProvider jwtProvider;

    @Override
    public void registration (@NonNull User user) {
        final String email = user.getEmail ();
        if (emailExists (email)) {
            String message = String.format ("There is an account with that email address: %s", email);
            throw new UserAlreadyExistException (message);
        }
        final String presentedPassword = user.getPassword ();
        final String encodePassword = BCrypt.hashpw (presentedPassword, BCrypt.gensalt ());
        user.setPassword (encodePassword);
        user.setEnabled (false);
        userService.save (user);
    }

    @Override
    @Transactional
    public void confirmRegistration (String token) {
        VerificationToken verificationToken =
                verificationTokenService.findByToken (token);
        User user = verificationToken.getUser ();

        if (validateToken (verificationToken)) {
            String message = String.format ("VerificationToken for user: %s,expired", user);
            throw new VerificationTokenProcessException (message);
        }
        user.setEnabled (true);
        userService.save (user);
        verificationTokenService.deleteByToken (token);
    }

    @Override
    public LoginResponse login (@NonNull LoginRequest loginRequest) {
        User user = fetchUser (loginRequest);
        verifyPassword (loginRequest, user);
        LoginResponse loginResponse = buildLoginResponse (user);
        return loginResponse;
    }

    @Override
    @Transactional
    public void blockUser (String email) {
        refreshTokenService.deleteByEmail (email);
        userService.deleteByEmail (email);
    }

    private boolean emailExists (String email) {
        try {
            userService.findByEmail (email);
            return true;
        } catch (UserNotFoundException ex) {
            return false;
        }
    }

    private boolean validateToken (VerificationToken verificationToken) {
        LocalDateTime now = LocalDateTime.now ();
        LocalDateTime toValid = verificationToken.getExpiryDate ().toLocalDateTime ();
        return now.isAfter (toValid);
    }

    private User fetchUser (LoginRequest loginRequest) {
        final String email = loginRequest.getEmail ();
        final User user = userService.findByEmail (email);

        if (!user.isEnabled ()) {
            String message = "The user's email verification to confirm their account wasn't or was unsuccessful";
            throw new UserLockedException (message);
        }
        return user;
    }

    private void verifyPassword (LoginRequest loginRequest, User user) {
        final String presentedPassword = loginRequest.getPassword ();
        final String savedPassword = user.getPassword ();

        if (!BCrypt.checkpw (presentedPassword, savedPassword)) {
            String message = String.format ("Entered incorrect password: %s", presentedPassword);
            throw new BadCredentialException (message);
        }
    }

    private LoginResponse buildLoginResponse (User user) {
        final String accessToken = jwtProvider.generateAccessToken (user);
        final String refreshToken = jwtProvider.generateRefreshToken (user);
        LoginResponse loginResponse = new LoginResponse (accessToken, refreshToken);
        return loginResponse;
    }
}
