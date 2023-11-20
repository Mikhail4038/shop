package com.keiko.zuulproxy.jwt.impl;

import com.keiko.zuulproxy.entity.JwtAuthentication;
import com.keiko.zuulproxy.entity.Role;
import com.keiko.zuulproxy.jwt.JwtProvider;
import com.keiko.zuulproxy.properties.JwtProperties;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.util.Set;

@Component
@Slf4j
public class DefaultJwtProvider implements JwtProvider {

    @Autowired
    private JwtProperties jwtProperties;
    private SecretKey accessKey;
    private SecretKey refreshKey;

    @PostConstruct
    public void init () {
        String jwtSecretAccess = jwtProperties.getJwtAccessSecret ();
        String jwtSecretRefresh = jwtProperties.getRefreshSecret ();

        //this.accessKey = Keys.hmacShaKeyFor (Decoders.BASE64.decode (jwtSecretAccess));
        //this.refreshKey = Keys.hmacShaKeyFor (Decoders.BASE64.decode (jwtSecretRefresh));
    }

    @Override
    public boolean validateAccessToken (String accessToken) {
        try {
            Jwts.parserBuilder ()
                    .setSigningKey (accessKey)
                    .build ()
                    .parseClaimsJws (accessToken);
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

    @Override
    public Claims getClaims (String token) {
        return Jwts.parserBuilder ()
                .setSigningKey (accessKey)
                .build ()
                .parseClaimsJws (token)
                .getBody ();
    }

    @Override
    public JwtAuthentication generate (Claims claims) {
        JwtAuthentication authentication = new JwtAuthentication ();
        authentication.setAuthenticated (true);
        authentication.setEmail (claims.getSubject ());
        authentication.setName (claims.get ("name", String.class));
        authentication.setRoles (getRoles (claims));
        final String email = claims.getSubject ();
        return null;
    }

    private Set<Role> getRoles (Claims claims) {
        Set<Role> roles = claims.get ("roles", Set.class);
        return roles;
    }
}