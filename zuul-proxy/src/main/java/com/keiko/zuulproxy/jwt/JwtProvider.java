package com.keiko.zuulproxy.jwt;

import com.keiko.zuulproxy.entity.JwtAuthentication;
import io.jsonwebtoken.Claims;

public interface JwtProvider {
    boolean validateAccessToken (String accessToken);

    Claims getClaims (String token);

    JwtAuthentication generate (Claims claims);
}
