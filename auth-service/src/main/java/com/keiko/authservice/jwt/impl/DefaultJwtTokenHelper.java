package com.keiko.authservice.jwt.impl;

import com.keiko.authservice.entity.User;
import com.keiko.authservice.entity.jwt.JwtRefreshToken;
import com.keiko.authservice.jwt.JwtProvider;
import com.keiko.authservice.jwt.JwtTokenHelper;
import com.keiko.authservice.request.JwtRefreshRequest;
import com.keiko.authservice.response.JwtRefreshResponse;
import com.keiko.authservice.service.RefreshTokenService;
import com.keiko.authservice.service.UserService;
import io.jsonwebtoken.Claims;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DefaultJwtTokenHelper implements JwtTokenHelper {

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private UserService userService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Override
    public JwtRefreshResponse getAccessToken (@NonNull JwtRefreshRequest jwtRefreshRequest) {

        final String presentedRefreshToken = jwtRefreshRequest.getRefreshToken ();
        if (jwtProvider.validateRefreshToken (presentedRefreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims (presentedRefreshToken);
            final String email = claims.getSubject ();

            if (verifyRefreshToken (presentedRefreshToken, email)) {
                final User user = userService.findByEmail (email);
                final String accessToken = jwtProvider.generateAccessToken (user);
                return new JwtRefreshResponse (accessToken, null);
            }
        }
        return new JwtRefreshResponse (null, null);
    }

    @Override
    public JwtRefreshResponse getRefreshToken (@NonNull JwtRefreshRequest jwtRefreshRequest) {
        final String presentedRefreshToken = jwtRefreshRequest.getRefreshToken ();
        if (jwtProvider.validateRefreshToken (presentedRefreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims (presentedRefreshToken);
            final String email = claims.getSubject ();

            if (verifyRefreshToken (presentedRefreshToken, email)) {
                final User user = userService.findByEmail (email);
                final String accessToken = jwtProvider.generateAccessToken (user);
                final String refreshToken = jwtProvider.generateRefreshToken (user);
                JwtRefreshToken jwtRefreshToken = new JwtRefreshToken (email, refreshToken);
                refreshTokenService.save (jwtRefreshToken);
                return new JwtRefreshResponse (accessToken, refreshToken);
            }
        }
        return new JwtRefreshResponse (null, null);
    }

    private boolean verifyRefreshToken (String presentedRefreshToken, String email) {
        final JwtRefreshToken savedRefreshToken = refreshTokenService.findByEmail (email);
        final String token = savedRefreshToken.getRefreshToken ();

        return presentedRefreshToken.equals (token);
    }
}
