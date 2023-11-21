package com.keiko.zuulproxy.jwt;

import com.keiko.zuulproxy.entity.JwtAuthentication;
import com.keiko.zuulproxy.jwt.impl.DefaultJwtProvider;
import io.jsonwebtoken.Claims;
import org.junit.Before;
import org.junit.Test;

import static com.keiko.zuulproxy.util.TestData.createTestClaims;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class JwtProviderUnitTest {

    private static JwtProvider jwtProvider;
    private static Claims claims;

    @Before
    public void setUp () {
        jwtProvider = new DefaultJwtProvider ();
        claims = createTestClaims ();
    }

    @Test
    public void should_successfully_generate_auth () {
        JwtAuthentication authentication = jwtProvider.generate (claims);
        assertEquals (authentication.isAuthenticated (), true);
        assertEquals (authentication.getEmail (), claims.getSubject ());
        assertEquals (authentication.getName (), claims.get ("name"));
        assertTrue (authentication.getRoles ().isEmpty ());
    }
}
