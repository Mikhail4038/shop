package com.keiko.authservice.util;

import com.keiko.authservice.entity.Role;
import com.keiko.authservice.entity.User;
import com.keiko.authservice.entity.VerificationToken;
import com.keiko.authservice.entity.jwt.JwtRefreshToken;
import com.keiko.authservice.event.OnRegistrationCompleteEvent;
import com.keiko.authservice.request.JwtRefreshRequest;
import com.keiko.authservice.request.LoginRequest;
import com.keiko.authservice.response.JwtRefreshResponse;
import com.keiko.authservice.response.LoginResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.security.crypto.bcrypt.BCrypt;

public class TestData {

    // user
    private static final String USER_EMAIL = "admin@gmail.com";
    private static final String USER_PASSWORD = BCrypt.hashpw ("500290", BCrypt.gensalt ());
    private static final String USER_NAME = "test";

    // role
    private static final String ROLE_NAME = "ADMIN";

    // verification token
    private static final String VERIFICATION_TOKEN_VALUE = "verify";

    // refresh token
    private static final String REFRESH_TOKEN_VALUE = "refresh";

    // login response
    private static final String ACCESS_TOKEN = "access";
    private static final String REFRESH_TOKEN = "refresh";

    // refresh response
    private static final String NEW_ACCESS_TOKEN_VALUE = "newAccessToken";
    private static final String NEW_REFRESH_TOKEN_VALUE = "newRefreshToken";


    public static User createTestUser () {
        User user = new User ();
        user.setEmail (USER_EMAIL);
        user.setPassword (USER_PASSWORD);
        user.setName (USER_NAME);
        return user;
    }

    public static Role createTestRole () {
        Role role = new Role ();
        role.setName (ROLE_NAME);
        return role;
    }

    public static VerificationToken createTestVerificationToken () {
        VerificationToken verificationToken = new VerificationToken ();
        verificationToken.setToken (VERIFICATION_TOKEN_VALUE);
        return verificationToken;
    }

    public static JwtRefreshToken createTestRefreshToken () {
        JwtRefreshToken token = new JwtRefreshToken ();
        token.setEmail (USER_EMAIL);
        token.setRefreshToken (REFRESH_TOKEN_VALUE);
        return token;
    }

    public static LoginRequest createTestLoginRequest () {
        LoginRequest request = new LoginRequest ();
        request.setEmail (USER_EMAIL);
        return request;
    }

    public static LoginResponse createTestLoginResponse () {
        LoginResponse response = new LoginResponse ();
        response.setAccessToken (ACCESS_TOKEN);
        response.setRefreshToken (REFRESH_TOKEN);
        return response;
    }

    public static JwtRefreshRequest createTestJwtRefreshRequest () {
        JwtRefreshRequest jwtRefreshRequest = new JwtRefreshRequest ();
        jwtRefreshRequest.setRefreshToken (REFRESH_TOKEN_VALUE);
        return jwtRefreshRequest;
    }

    public static JwtRefreshResponse createTestJwtRefreshResponse () {
        JwtRefreshResponse response = new JwtRefreshResponse ();
        response.setAccessToken (NEW_ACCESS_TOKEN_VALUE);
        response.setRefreshToken (NEW_REFRESH_TOKEN_VALUE);
        return new JwtRefreshResponse ();
    }

    public static Claims createTestClaims () {
        Claims claims = new DefaultClaims ();
        claims.setSubject (USER_EMAIL);
        return claims;
    }

    public static OnRegistrationCompleteEvent createTestEvent () {
        OnRegistrationCompleteEvent event
                = new OnRegistrationCompleteEvent (createTestUser ());
        return event;
    }
}
