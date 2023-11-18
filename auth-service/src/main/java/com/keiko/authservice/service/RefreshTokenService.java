package com.keiko.authservice.service;

import com.keiko.authservice.entity.jwt.JwtRefreshToken;
import lombok.NonNull;

public interface RefreshTokenService {

    void save (@NonNull JwtRefreshToken jwtRefreshToken);

    void deleteByEmail (String refreshToken);

    JwtRefreshToken findByEmail (String email);
}
