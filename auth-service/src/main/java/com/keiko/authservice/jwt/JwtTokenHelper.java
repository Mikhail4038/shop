package com.keiko.authservice.jwt;

import com.keiko.authservice.request.JwtRefreshRequest;
import com.keiko.authservice.response.JwtRefreshResponse;
import lombok.NonNull;

public interface JwtTokenHelper {
    JwtRefreshResponse generateAccessToken (@NonNull JwtRefreshRequest jwtRefreshRequest);

    JwtRefreshResponse generateRefreshToken (@NonNull JwtRefreshRequest jwtRefreshRequest);
}
