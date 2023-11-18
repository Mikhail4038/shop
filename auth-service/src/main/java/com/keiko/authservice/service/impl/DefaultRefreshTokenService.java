package com.keiko.authservice.service.impl;

import com.keiko.authservice.entity.jwt.JwtRefreshToken;
import com.keiko.authservice.exception.model.JwtRefreshTokenProcessException;
import com.keiko.authservice.repository.RefreshTokenRepository;
import com.keiko.authservice.service.RefreshTokenService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultRefreshTokenService implements RefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Override
    public void save (@NonNull JwtRefreshToken jwtRefreshToken) {
        refreshTokenRepository.save (jwtRefreshToken);
    }

    @Override
    public void deleteByEmail (String email) {
        refreshTokenRepository.deleteById (email);
    }

    @Override
    public JwtRefreshToken findByEmail (String email) {
        return refreshTokenRepository.findByEmail (email)
                .orElseThrow (() -> new JwtRefreshTokenProcessException (String.format
                        ("Token with email: %s not found", email)));
    }
}
