package com.keiko.authservice.jwt;

import com.keiko.authservice.request.JwtRefreshRequest;
import com.keiko.authservice.response.JwtRefreshResponse;
import lombok.NonNull;

public interface JwtTokenHelper {
    JwtRefreshResponse getAccessToken (@NonNull JwtRefreshRequest jwtRefreshRequest);

    JwtRefreshResponse getRefreshToken (@NonNull JwtRefreshRequest jwtRefreshRequest);
}
