package com.keiko.authservice.service;

import com.keiko.authservice.entity.jwt.JwtRefreshToken;
import com.keiko.authservice.repository.RefreshTokenRepository;
import com.keiko.authservice.service.impl.DefaultRefreshTokenService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.keiko.authservice.util.TestData.testRefreshToken;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith (MockitoExtension.class)
class RefreshTokenServiceUnitTest {

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @InjectMocks
    private static RefreshTokenService refreshTokenService;
    private static JwtRefreshToken jwtRefreshToken;

    @BeforeAll
    static void setUp () {
        jwtRefreshToken = testRefreshToken ();
        refreshTokenService = new DefaultRefreshTokenService ();
    }

    @Test
    void should_successfully_save () {
        refreshTokenService.save (jwtRefreshToken);
        verify (refreshTokenRepository, times (1)).save (any (JwtRefreshToken.class));
        verifyNoMoreInteractions (refreshTokenRepository);
    }

    @Test
    void should_successfully_delete () {
        refreshTokenService.deleteByEmail (jwtRefreshToken.getEmail ());
        verify (refreshTokenRepository, times (1)).deleteById (anyString ());
        verifyNoMoreInteractions (refreshTokenRepository);
    }

    @Test
    void should_successfully_findByEmail () {
        final String email = jwtRefreshToken.getEmail ();
        when (refreshTokenRepository.findByEmail (email)).thenReturn (Optional.of (jwtRefreshToken));
        JwtRefreshToken result = refreshTokenService.findByEmail (email);
        assertEquals (result, jwtRefreshToken);
        verify (refreshTokenRepository, times (1)).findByEmail (anyString ());
        verifyNoMoreInteractions (refreshTokenRepository);
    }
}
