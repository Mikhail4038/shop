package com.keiko.authservice.repository;

import com.keiko.authservice.entity.jwt.JwtRefreshToken;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RefreshTokenRepository
        extends MongoRepository<JwtRefreshToken, String> {

    Optional<JwtRefreshToken> findByEmail (String email);
}
