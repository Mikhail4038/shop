package com.keiko.zuulproxy.filter;

import com.keiko.zuulproxy.jwt.JwtProvider;
import io.jsonwebtoken.Claims;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.keiko.zuulproxy.util.TestData.createTestClaims;
import static org.mockito.Mockito.*;

@RunWith (MockitoJUnitRunner.class)
public class JwtAuthFilterUnitTest {
    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer";
    private static final String INCORRECT_TOKEN = "token";
    private static final String CORRECT_TOKEN = "Bearer eyJhbGciOiJIUzUxMiJ9";

    @Mock
    private JwtProvider jwtProvider;

    @Mock
    private HttpServletRequest httpServletRequest;
    @Mock
    private HttpServletResponse httpServletResponse;

    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private JwtAuthFilter jwtAuthFilter;
    private Claims claims;

    @Test
    public void should_unSuccessfully_jwtAuth_token_is_null () throws ServletException, IOException {
        when (httpServletRequest.getHeader (AUTHORIZATION)).thenReturn (null);
        jwtAuthFilter.doFilterInternal (httpServletRequest, httpServletResponse, filterChain);
        verify (jwtProvider, times (0)).getClaims (anyString ());
        verify (jwtProvider, times (0)).generate (any (Claims.class));
    }

    @Test
    public void should_unSuccessfully_jwtAuth_incorrect_token () throws ServletException, IOException {
        when (httpServletRequest.getHeader (AUTHORIZATION)).thenReturn (INCORRECT_TOKEN);
        jwtAuthFilter.doFilterInternal (httpServletRequest, httpServletResponse, filterChain);
        verify (jwtProvider, times (0)).getClaims (anyString ());
        verify (jwtProvider, times (0)).generate (any (Claims.class));
    }

    @Test
    public void should_successfully_jwtAuth () throws ServletException, IOException {
        claims = createTestClaims ();

        when (httpServletRequest.getHeader (AUTHORIZATION)).thenReturn (CORRECT_TOKEN);
        when (jwtProvider.validateAccessToken (CORRECT_TOKEN.substring (7))).thenReturn (true);
        when (jwtProvider.getClaims (CORRECT_TOKEN.substring (7))).thenReturn (claims);

        jwtAuthFilter.doFilterInternal (httpServletRequest, httpServletResponse, filterChain);

        verify (jwtProvider, times (1)).getClaims (anyString ());
        verify (jwtProvider, times (1)).generate (any (Claims.class));
        InOrder orderJwtProvider = Mockito.inOrder (jwtProvider);
        orderJwtProvider.verify (jwtProvider).getClaims (anyString ());
        orderJwtProvider.verify (jwtProvider).generate (any (Claims.class));
    }
}
