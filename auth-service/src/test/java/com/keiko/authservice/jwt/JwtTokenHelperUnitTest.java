package com.keiko.authservice.jwt;

import com.keiko.authservice.entity.User;
import com.keiko.authservice.entity.jwt.JwtRefreshToken;
import com.keiko.authservice.jwt.impl.DefaultJwtTokenHelper;
import com.keiko.authservice.request.JwtRefreshRequest;
import com.keiko.authservice.response.JwtRefreshResponse;
import com.keiko.authservice.service.RefreshTokenService;
import com.keiko.authservice.service.UserService;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.keiko.authservice.util.TestData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith (MockitoExtension.class)
class JwtTokenHelperUnitTest {
    private static final String REFRESH_TOKEN_INCORRECT_VALUE = "incorrect";
    private static final String REFRESH_TOKEN_CORRECT_VALUE = "refresh";
    private static final String REFRESH_TOKEN_NEW_VALUE = "new";
    private static final String ACCESS_TOKEN_VALUE = "access";

    @Mock
    private JwtProvider jwtProvider;

    @Mock
    private UserService userService;

    @Mock
    private RefreshTokenService refreshTokenService;

    @InjectMocks
    private static JwtTokenHelper jwtTokenHelper;
    private static JwtRefreshRequest jwtRefreshRequest;
    private static Claims claims;
    private static JwtRefreshToken jwtRefreshToken;
    private static User user;

    @BeforeAll
    static void setUp () {
        jwtTokenHelper = new DefaultJwtTokenHelper ();
        jwtRefreshRequest = createTestJwtRefreshRequest ();
        claims = createTestClaims ();
        jwtRefreshToken = createTestRefreshToken ();
        user = createTestUser ();
    }

    @Test
    void should_unSuccessfully_generate_newAccessToken_invalid_refreshToken () {
        final String refreshToken = jwtRefreshRequest.getRefreshToken ();
        when (jwtProvider.validateRefreshToken (refreshToken)).thenReturn (false);

        JwtRefreshResponse jwtRefreshResponse = jwtTokenHelper.getAccessToken (jwtRefreshRequest);

        assertNull (jwtRefreshResponse.getAccessToken ());
        assertNull (jwtRefreshResponse.getRefreshToken ());
        verify (jwtProvider, times (1)).validateRefreshToken (anyString ());
        verifyNoMoreInteractions (jwtProvider);
    }

    @Test
    void should_unSuccessfully_generate_newAccessToken_refreshToken_not_match () {
        final String refreshToken = jwtRefreshRequest.getRefreshToken ();
        final String email = claims.getSubject ();
        jwtRefreshToken.setRefreshToken (REFRESH_TOKEN_INCORRECT_VALUE);

        when (jwtProvider.validateRefreshToken (refreshToken)).thenReturn (true);
        when (jwtProvider.getRefreshClaims (refreshToken)).thenReturn (claims);
        when (refreshTokenService.findByEmail (email)).thenReturn (jwtRefreshToken);

        JwtRefreshResponse jwtRefreshResponse = jwtTokenHelper.getAccessToken (jwtRefreshRequest);

        assertNull (jwtRefreshResponse.getAccessToken ());
        assertNull (jwtRefreshResponse.getRefreshToken ());

        verify (jwtProvider, times (1)).validateRefreshToken (anyString ());
        verify (jwtProvider, times (1)).getRefreshClaims (anyString ());
        verifyNoMoreInteractions (jwtProvider);
        InOrder orderJwtProvider = inOrder (jwtProvider);
        orderJwtProvider.verify (jwtProvider).validateRefreshToken (anyString ());
        orderJwtProvider.verify (jwtProvider).getRefreshClaims (anyString ());

        verify (refreshTokenService, times (1)).findByEmail (anyString ());
        verifyNoMoreInteractions (refreshTokenService);
    }

    @Test
    void should_successfully_generate_newAccessToken () {
        final String refreshToken = jwtRefreshRequest.getRefreshToken ();
        final String email = claims.getSubject ();
        jwtRefreshToken.setRefreshToken (REFRESH_TOKEN_CORRECT_VALUE);

        when (jwtProvider.validateRefreshToken (refreshToken)).thenReturn (true);
        when (jwtProvider.getRefreshClaims (refreshToken)).thenReturn (claims);
        when (refreshTokenService.findByEmail (email)).thenReturn (jwtRefreshToken);
        when (userService.findByEmail (email)).thenReturn (user);
        when (jwtProvider.generateAccessToken (user)).thenReturn (ACCESS_TOKEN_VALUE);

        JwtRefreshResponse jwtRefreshResponse = jwtTokenHelper.getAccessToken (jwtRefreshRequest);
        final String accessTokenValue = jwtRefreshResponse.getAccessToken ();

        assertNotNull (accessTokenValue);
        assertEquals (accessTokenValue, ACCESS_TOKEN_VALUE);
        assertNull (jwtRefreshResponse.getRefreshToken ());

        verify (jwtProvider, times (1)).validateRefreshToken (anyString ());
        verify (jwtProvider, times (1)).getRefreshClaims (anyString ());
        verify (jwtProvider, times (1)).generateAccessToken (any (User.class));
        verifyNoMoreInteractions (jwtProvider);
        InOrder orderJwtProvider = inOrder (jwtProvider);
        orderJwtProvider.verify (jwtProvider).validateRefreshToken (anyString ());
        orderJwtProvider.verify (jwtProvider).getRefreshClaims (anyString ());
        orderJwtProvider.verify (jwtProvider).generateAccessToken (any (User.class));

        verify (refreshTokenService, times (1)).findByEmail (anyString ());
        verifyNoMoreInteractions (refreshTokenService);

        verify (userService, times (1)).findByEmail (anyString ());
        verifyNoMoreInteractions (userService);
    }

    @Test
    void should_unSuccessfully_generate_newRefreshToken_invalid_refreshToken () {
        final String refreshToken = jwtRefreshRequest.getRefreshToken ();

        when (jwtProvider.validateRefreshToken (refreshToken)).thenReturn (false);

        JwtRefreshResponse jwtRefreshResponse = jwtTokenHelper.getRefreshToken (jwtRefreshRequest);
        assertNull (jwtRefreshResponse.getAccessToken ());
        assertNull (jwtRefreshResponse.getRefreshToken ());

        verify (jwtProvider, times (1)).validateRefreshToken (anyString ());
        verifyNoMoreInteractions (jwtProvider);
    }

    @Test
    void should_unSuccessfully_generate_newRefreshToken_refreshToken_not_match () {
        final String refreshToken = jwtRefreshRequest.getRefreshToken ();
        final String email = claims.getSubject ();
        jwtRefreshToken.setRefreshToken (REFRESH_TOKEN_INCORRECT_VALUE);

        when (jwtProvider.validateRefreshToken (refreshToken)).thenReturn (true);
        when (jwtProvider.getRefreshClaims (refreshToken)).thenReturn (claims);
        when (refreshTokenService.findByEmail (email)).thenReturn (jwtRefreshToken);

        JwtRefreshResponse jwtRefreshResponse = jwtTokenHelper.getRefreshToken (jwtRefreshRequest);

        assertNull (jwtRefreshResponse.getAccessToken ());
        assertNull (jwtRefreshResponse.getRefreshToken ());

        verify (jwtProvider, times (1)).validateRefreshToken (anyString ());
        verify (jwtProvider, times (1)).getRefreshClaims (anyString ());
        InOrder orderJwtProvider = inOrder (jwtProvider);
        orderJwtProvider.verify (jwtProvider).validateRefreshToken (anyString ());
        orderJwtProvider.verify (jwtProvider).getRefreshClaims (anyString ());
        verifyNoMoreInteractions (jwtProvider);

        verify (refreshTokenService, times (1)).findByEmail (anyString ());
        verifyNoMoreInteractions (refreshTokenService);
    }

    @Test
    void should_successfully_generate_newRefreshToken () {
        final String refreshToken = jwtRefreshRequest.getRefreshToken ();
        final String email = claims.getSubject ();
        jwtRefreshToken.setRefreshToken (REFRESH_TOKEN_CORRECT_VALUE);

        when (jwtProvider.validateRefreshToken (refreshToken)).thenReturn (true);
        when (jwtProvider.getRefreshClaims (refreshToken)).thenReturn (claims);
        when (refreshTokenService.findByEmail (email)).thenReturn (jwtRefreshToken);
        when (userService.findByEmail (email)).thenReturn (user);
        when (jwtProvider.generateAccessToken (user)).thenReturn (ACCESS_TOKEN_VALUE);
        when (jwtProvider.generateRefreshToken (user)).thenReturn (REFRESH_TOKEN_NEW_VALUE);

        JwtRefreshResponse jwtRefreshResponse = jwtTokenHelper.getRefreshToken (jwtRefreshRequest);
        final String accessTokenValue = jwtRefreshResponse.getAccessToken ();
        final String refreshTokenValue = jwtRefreshResponse.getRefreshToken ();

        assertNotNull (accessTokenValue);
        assertEquals (accessTokenValue, ACCESS_TOKEN_VALUE);
        assertNotNull (refreshTokenValue);
        assertEquals (refreshTokenValue, REFRESH_TOKEN_NEW_VALUE);

        verify (userService, times (1)).findByEmail (anyString ());
        verifyNoMoreInteractions (userService);

        verify (refreshTokenService, times (1)).findByEmail (anyString ());
        verify (refreshTokenService, times (1)).save (any (JwtRefreshToken.class));
        InOrder orderRefreshTokenService = inOrder (refreshTokenService);
        orderRefreshTokenService.verify (refreshTokenService).findByEmail (anyString ());
        orderRefreshTokenService.verify (refreshTokenService).save (any (JwtRefreshToken.class));
        verifyNoMoreInteractions (refreshTokenService);

        verify (jwtProvider, times (1)).validateRefreshToken (anyString ());
        verify (jwtProvider, times (1)).getRefreshClaims (anyString ());
        verify (jwtProvider, times (1)).generateAccessToken (any (User.class));
        verify (jwtProvider, times (1)).generateRefreshToken (any (User.class));
        verifyNoMoreInteractions (refreshTokenService);
        InOrder orderJwtProvider = inOrder (jwtProvider);
        orderJwtProvider.verify (jwtProvider).validateRefreshToken (anyString ());
        orderJwtProvider.verify (jwtProvider).getRefreshClaims (anyString ());
        orderJwtProvider.verify (jwtProvider).generateAccessToken (any (User.class));
        orderJwtProvider.verify (jwtProvider).generateRefreshToken (any (User.class));
    }
}
