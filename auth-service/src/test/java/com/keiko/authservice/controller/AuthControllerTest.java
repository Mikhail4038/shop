package com.keiko.authservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.keiko.authservice.entity.User;
import com.keiko.authservice.jwt.JwtTokenHelper;
import com.keiko.authservice.request.JwtRefreshRequest;
import com.keiko.authservice.request.LoginRequest;
import com.keiko.authservice.response.JwtRefreshResponse;
import com.keiko.authservice.response.LoginResponse;
import com.keiko.authservice.service.impl.DefaultAuthService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.web.servlet.MockMvc;

import static com.keiko.authservice.constants.WebResourceKeyConstants.*;
import static com.keiko.authservice.util.TestData.*;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest (AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ApplicationEventPublisher eventPublisher;

    @MockBean
    private DefaultAuthService authService;

    @MockBean
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private ObjectMapper objectMapper;
    private static User user;
    private static LoginRequest loginRequest;
    private static LoginResponse loginResponse;
    private static JwtRefreshRequest jwtRefreshRequest;
    private static JwtRefreshResponse jwtRefreshResponse;

    @BeforeAll
    static void setUp () {
        user = testUser ();
        loginRequest = testLoginRequest ();
        loginResponse = testLoginResponse ();
        jwtRefreshRequest = testJwtRefreshRequest ();
        jwtRefreshResponse = testJwtRefreshResponse ();
    }

    @Test
    void registration_should_successfully () throws Exception {
        mockMvc.perform (post (AUTH_BASE + REGISTRATION_USER)
                .content (objectMapper.writeValueAsString (user))
                .contentType (APPLICATION_JSON_VALUE))
                .andExpect (status ().isOk ());

        verify (authService, times (1)).registration (any (User.class));
        verifyNoMoreInteractions (authService);
    }

    @Test
    void confirm_registration_should_successfully () throws Exception {
        mockMvc.perform (get (AUTH_BASE + CONFIRM_REGISTRATION)
                .queryParam ("token", "25922")
                .contentType (APPLICATION_JSON_VALUE))
                .andExpect (status ().isOk ());

        verify (authService, times (1)).confirmRegistration (anyString ());
        verifyNoMoreInteractions (authService);
    }

    @Test
    void login_should_successfully () throws Exception {
        when (authService.login (loginRequest)).thenReturn (loginResponse);

        mockMvc.perform (post (AUTH_BASE + LOGIN_USER)
                .content (objectMapper.writeValueAsString (loginRequest))
                .contentType (APPLICATION_JSON_VALUE))
                .andExpect (jsonPath ("$.type", is (loginResponse.getType ())))
                .andExpect (jsonPath ("$.accessToken", is (loginResponse.getAccessToken ())))
                .andExpect (jsonPath ("$.refreshToken", is (loginResponse.getRefreshToken ())))
                .andExpect (status ().isOk ());

        verify (authService, times (1)).login (any (LoginRequest.class));
        verifyNoMoreInteractions (authService);
    }

    @Test
    void block_user_should_successfully () throws Exception {
        mockMvc.perform (get (AUTH_BASE + BLOCK_USER)
                .queryParam ("email", user.getEmail ())
                .contentType (APPLICATION_JSON_VALUE))
                .andExpect (status ().isOk ());

        verify (authService, times (1)).blockUser (anyString ());
        verifyNoMoreInteractions (authService);
    }

    @Test
    void generate_new_access_token_should_successfully () throws Exception {
        when (jwtTokenHelper.getAccessToken (jwtRefreshRequest)).thenReturn (jwtRefreshResponse);

        mockMvc.perform (post (AUTH_BASE + GENERATE_NEW_ACCESS_TOKEN)
                .content (objectMapper.writeValueAsString (jwtRefreshRequest))
                .contentType (APPLICATION_JSON_VALUE))
                .andExpect (jsonPath ("$.accessToken", is (jwtRefreshResponse.getAccessToken ())))
                .andExpect (jsonPath ("$.refreshToken", is (jwtRefreshResponse.getRefreshToken ())))
                .andExpect (status ().isOk ());

        verify (jwtTokenHelper, times (1)).getAccessToken (any (JwtRefreshRequest.class));
        verifyNoMoreInteractions (jwtTokenHelper);
    }

    @Test
    void generate_new_refresh_token_should_successfully () throws Exception {
        when (jwtTokenHelper.getRefreshToken (jwtRefreshRequest)).thenReturn (jwtRefreshResponse);

        mockMvc.perform (post (AUTH_BASE + GENERATE_NEW_REFRESH_TOKEN)
                .content (objectMapper.writeValueAsString (jwtRefreshRequest))
                .contentType (APPLICATION_JSON_VALUE))
                .andExpect (jsonPath ("$.accessToken", is (jwtRefreshResponse.getAccessToken ())))
                .andExpect (jsonPath ("$.refreshToken", is (jwtRefreshResponse.getRefreshToken ())))
                .andExpect (status ().isOk ());

        verify (jwtTokenHelper, times (1)).getRefreshToken (any (JwtRefreshRequest.class));
        verifyNoMoreInteractions (jwtTokenHelper);
    }
}
