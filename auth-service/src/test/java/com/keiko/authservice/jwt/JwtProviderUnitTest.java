package com.keiko.authservice.jwt;

import com.keiko.authservice.entity.Role;
import com.keiko.authservice.entity.User;
import com.keiko.authservice.entity.jwt.JwtRefreshToken;
import com.keiko.authservice.jwt.impl.DefaultJwtProvider;
import com.keiko.authservice.properties.JwtProperties;
import com.keiko.authservice.service.RefreshTokenService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static com.keiko.authservice.util.TestData.createTestRole;
import static com.keiko.authservice.util.TestData.createTestUser;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith (MockitoExtension.class)
class JwtProviderUnitTest {
    private static final String ACCESS_SECRET = "QVZlcnlEaWZmaWN1bHRBY2Nlc3NTZWNyZXRGb3JUaGVUZXN0aW5n";
    private static final String REFRESH_SECRET = "QVZlcnlEaWZmaWN1bHRSZWZyZXNoU2VjcmV0Rm9yVGhlVGVzdGluZw==";
    private static final Long VALID_ACCESS_TOKEN = 15L;
    private static final Long VALID_REFRESH_TOKEN = 30L;

    @Mock
    private RefreshTokenService refreshTokenService;

    @Mock
    private JwtProperties jwtProperties;

    @InjectMocks
    private static DefaultJwtProvider jwtProvider;

    private static User user;
    private static Role role;

    @BeforeAll
    static void setUp () {
        user = createTestUser ();
        role = createTestRole ();
        user.setRoles (Set.of (role));
    }

    @Test
    void should_successfully_generate_access_token () {
        when (jwtProperties.getAccessSecret ()).thenReturn (ACCESS_SECRET);
        when (jwtProperties.getRefreshSecret ()).thenReturn (REFRESH_SECRET);
        jwtProvider.init ();

        when (jwtProperties.getValidityPeriodAccessToken ()).thenReturn (VALID_ACCESS_TOKEN);
        final String accessToken = jwtProvider.generateAccessToken (user);
        assertTrue (isNotBlank (accessToken));

        verify (jwtProperties, times (1)).getAccessSecret ();
        verify (jwtProperties, times (1)).getRefreshSecret ();
        verify (jwtProperties, times (1)).getValidityPeriodAccessToken ();
        verifyNoMoreInteractions (jwtProperties);
        InOrder orderJwtProperties = Mockito.inOrder (jwtProperties);
        orderJwtProperties.verify (jwtProperties).getAccessSecret ();
        orderJwtProperties.verify (jwtProperties).getRefreshSecret ();
        orderJwtProperties.verify (jwtProperties).getValidityPeriodAccessToken ();
    }

    @Test
    void should_successfully_generate_refresh_token () {
        when (jwtProperties.getAccessSecret ()).thenReturn (ACCESS_SECRET);
        when (jwtProperties.getRefreshSecret ()).thenReturn (REFRESH_SECRET);
        jwtProvider.init ();

        when (jwtProperties.getValidityPeriodRefreshToken ()).thenReturn (VALID_REFRESH_TOKEN);
        final String accessToken = jwtProvider.generateRefreshToken (user);
        assertTrue (isNotBlank (accessToken));

        verify (refreshTokenService, times (1)).save (any (JwtRefreshToken.class));

        verify (jwtProperties, times (1)).getAccessSecret ();
        verify (jwtProperties, times (1)).getRefreshSecret ();
        verify (jwtProperties, times (1)).getValidityPeriodRefreshToken ();
        verifyNoMoreInteractions (jwtProperties);
        InOrder orderJwtProperties = Mockito.inOrder (jwtProperties);
        orderJwtProperties.verify (jwtProperties).getAccessSecret ();
        orderJwtProperties.verify (jwtProperties).getRefreshSecret ();
        orderJwtProperties.verify (jwtProperties).getValidityPeriodRefreshToken ();
    }

}
