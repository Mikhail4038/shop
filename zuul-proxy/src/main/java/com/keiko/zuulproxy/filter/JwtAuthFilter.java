package com.keiko.zuulproxy.filter;

import com.keiko.zuulproxy.jwt.JwtProvider;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.util.Objects.nonNull;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer";

    @Autowired
    private JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal (HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String token = getTokenFromRequest (request);
        if (nonNull (token) && jwtProvider.validateAccessToken (token)) {
            Claims claims = jwtProvider.getClaims (token);
            Authentication authentication = jwtProvider.generate (claims);
            SecurityContextHolder.getContext ().setAuthentication (authentication);
        }
        filterChain.doFilter (request, response);
    }

    private String getTokenFromRequest (HttpServletRequest request) {
        String authHeader = request.getHeader (AUTHORIZATION);
        if (StringUtils.isNotBlank (authHeader) && authHeader.startsWith (BEARER)) {
            return authHeader.substring (7);
        }
        return null;
    }
}
