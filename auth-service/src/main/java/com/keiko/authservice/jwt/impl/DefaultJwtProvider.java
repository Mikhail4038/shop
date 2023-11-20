package com.keiko.authservice.jwt.impl;

import com.keiko.authservice.entity.Role;
import com.keiko.authservice.entity.User;
import com.keiko.authservice.entity.jwt.JwtRefreshToken;
import com.keiko.authservice.jwt.JwtProvider;
import com.keiko.authservice.properties.JwtProperties;
import com.keiko.authservice.service.RefreshTokenService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.Set;

import static java.time.LocalDateTime.now;
import static java.time.ZoneId.systemDefault;

@Component
@Slf4j
public class DefaultJwtProvider implements JwtProvider {

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private RefreshTokenService refreshTokenService;

    private SecretKey accessKey;
    private SecretKey refreshKey;

    @PostConstruct
    public void init () {
        String jwtSecretAccess = jwtProperties.getAccessSecret ();
        String jwtSecretRefresh = jwtProperties.getRefreshSecret ();

        this.accessKey = Keys.hmacShaKeyFor (Decoders.BASE64.decode (jwtSecretAccess));
        this.refreshKey = Keys.hmacShaKeyFor (Decoders.BASE64.decode (jwtSecretRefresh));
    }

    @Override
    public String generateAccessToken (@NonNull User user) {
        final String email = user.getEmail ();
        final String name = user.getName ();
        final Set<Role> roles = user.getRoles ();

        final Long validityPeriod = jwtProperties.getValidityPeriodAccessToken ();
        final Instant accessExpirationInstant = now ().plusMinutes (validityPeriod).atZone (systemDefault ()).toInstant ();
        final Date accessExpiration = Date.from (accessExpirationInstant);

        return Jwts
                .builder ()
                .signWith (accessKey)
                .setExpiration (accessExpiration)
                .setSubject (email)
                .claim ("role", roles)
                .claim ("name", name)
                .compact ();
    }

    @Override
    public String generateRefreshToken (@NonNull User user) {
        final String email = user.getEmail ();
        final Long validityPeriod = jwtProperties.getValidityPeriodRefreshToken ();
        final Instant accessExpirationInstant = now ().plusDays (validityPeriod).atZone (systemDefault ()).toInstant ();
        final Date accessExpiration = Date.from (accessExpirationInstant);

        String refreshToken = Jwts.builder ()
                .signWith (refreshKey)
                .setExpiration (accessExpiration)
                .setSubject (email)
                .compact ();
        JwtRefreshToken jwtRefreshToken = new JwtRefreshToken (email, refreshToken);
        refreshTokenService.save (jwtRefreshToken);

        return refreshToken;
    }

    @Override
    public boolean validateRefreshToken (@NonNull String refreshToken) {
        return validateToken (refreshToken, refreshKey);
    }

    @Override
    public Claims getRefreshClaims (String refreshToken) {
        Claims claims = getClaims (refreshToken, refreshKey);
        return claims;
    }

    private boolean validateToken (String token, SecretKey secretKey) {
        try {
            Jwts.parserBuilder ()
                    .setSigningKey (secretKey)
                    .build ()
                    .parseClaimsJws (token);
            return true;
        } catch (ExpiredJwtException expEx) {
            log.error ("Token expired", expEx);
        } catch (UnsupportedJwtException unsEx) {
            log.error ("Unsupported jwt", unsEx);
        } catch (MalformedJwtException mjEx) {
            log.error ("Malformed jwt", mjEx);
        } catch (SignatureException sEx) {
            log.error ("Invalid signature", sEx);
        } catch (Exception e) {
            log.error ("invalid token", e);
        }
        return false;
    }

    private Claims getClaims (String token, SecretKey secretKey) {
        return Jwts.parserBuilder ()
                .setSigningKey (secretKey)
                .build ()
                .parseClaimsJws (token)
                .getBody ();
    }
}
