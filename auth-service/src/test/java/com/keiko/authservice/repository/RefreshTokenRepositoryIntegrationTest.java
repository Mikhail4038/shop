package com.keiko.authservice.repository;

import com.keiko.authservice.entity.jwt.JwtRefreshToken;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@ExtendWith (SpringExtension.class)
class RefreshTokenRepositoryIntegrationTest {
    private static final String USER_EMAIL = "admin@gmail.com";
    private static final String REFRESH_TOKEN = "token";

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Test
    void whenFindByEmail_thenReturnRefreshToken () {
        JwtRefreshToken jwtRefreshToken = new JwtRefreshToken (USER_EMAIL, REFRESH_TOKEN);
        refreshTokenRepository.save (jwtRefreshToken);
        assertThat (refreshTokenRepository.findByEmail (USER_EMAIL)).isNotEmpty ();
    }
}
