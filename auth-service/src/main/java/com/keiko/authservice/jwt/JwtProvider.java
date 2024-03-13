package com.keiko.authservice.jwt;

import com.keiko.commonservice.entity.resource.user.User;
import io.jsonwebtoken.Claims;
import lombok.NonNull;

/**
 * Generate and validate jwt token (access, refresh)
 */
public interface JwtProvider {
    String generateAccessToken (@NonNull User user);

    String generateRefreshToken (@NonNull User user);

    boolean validateRefreshToken (@NonNull String refreshToken);

    Claims getRefreshClaims (String refreshToken);
}
