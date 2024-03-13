package com.keiko.authservice.service.impl;

import com.keiko.authservice.entity.VerificationToken;
import com.keiko.authservice.exception.model.BadCredentialException;
import com.keiko.authservice.exception.model.UserAlreadyExistException;
import com.keiko.authservice.exception.model.UserLockedException;
import com.keiko.authservice.exception.model.VerificationTokenProcessException;
import com.keiko.authservice.jwt.JwtProvider;
import com.keiko.authservice.request.LoginRequest;
import com.keiko.authservice.request.RegistrationRequest;
import com.keiko.authservice.response.LoginResponse;
import com.keiko.authservice.service.AuthService;
import com.keiko.authservice.service.RefreshTokenService;
import com.keiko.authservice.service.VerificationTokenService;
import com.keiko.authservice.service.resources.UserService;
import com.keiko.commonservice.entity.resource.Address;
import com.keiko.commonservice.entity.resource.user.Role;
import com.keiko.commonservice.entity.resource.user.User;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.Set;

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
    public void registration (@NonNull RegistrationRequest registrationRequest) {
        final String email = registrationRequest.getEmail ();
        if (emailExists (email)) {
            String message = String.format ("There is an account with that email address: %s", email);
            throw new UserAlreadyExistException (message);
        }

        User user = createNewUser (registrationRequest);
        userService.save (user);
    }

    @Override
    @Transactional
    public void confirmRegistration (String token) {
        VerificationToken verificationToken =
                verificationTokenService.findByToken (token);
        String email = verificationToken.getEmail ();

        if (validateToken (verificationToken)) {
            String message = String.format ("VerificationToken for user: %s,expired", email);
            throw new VerificationTokenProcessException (message);
        }
        User user = userService.findByEmail (email);
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
        return userService.isExists (email);
    }

    private boolean validateToken (VerificationToken verificationToken) {
        LocalDateTime now = LocalDateTime.now ();
        LocalDateTime toValid = verificationToken.getExpiryDate ();
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

    private User createNewUser (RegistrationRequest registrationRequest) {
        User user = new User ();

        final String email = registrationRequest.getEmail ();
        final String name = registrationRequest.getName ();
        final Address address = registrationRequest.getAddress ();
        final String presentedPassword = registrationRequest.getPassword ();
        final String encodePassword = BCrypt.hashpw (presentedPassword, BCrypt.gensalt ());
        final Long roleId = registrationRequest.getRoleId ();

        Role role = userService.findRoleById (roleId);
        user.setEmail (email);
        user.setPassword (encodePassword);
        user.setName (name);
        user.setEnabled (false);
        user.setRoles (Set.of (role));
        user.setUserAddress (address);
        return user;
    }
}
