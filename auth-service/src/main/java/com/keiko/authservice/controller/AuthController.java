package com.keiko.authservice.controller;

import com.keiko.authservice.entity.User;
import com.keiko.authservice.event.OnRegistrationCompleteEvent;
import com.keiko.authservice.jwt.JwtTokenHelper;
import com.keiko.authservice.request.JwtRefreshRequest;
import com.keiko.authservice.request.LoginRequest;
import com.keiko.authservice.response.JwtRefreshResponse;
import com.keiko.authservice.response.LoginResponse;
import com.keiko.authservice.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.keiko.authservice.constants.WebResourceKeyConstants.*;

@RestController
@RequestMapping (value = AUTH_BASE)
public class AuthController {

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @PostMapping (value = REGISTRATION_USER)
    public ResponseEntity registration (@RequestBody User user) {
        authService.registration (user);
        eventPublisher.publishEvent (new OnRegistrationCompleteEvent (user));
        return ResponseEntity.ok ().build ();
    }

    @GetMapping (value = CONFIRM_REGISTRATION)
    public ResponseEntity confirmRegistration (@RequestParam String token) {
        authService.confirmRegistration (token);
        return ResponseEntity.ok ().build ();
    }

    @PostMapping (value = LOGIN_USER)
    public ResponseEntity<LoginResponse> login (@RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = authService.login (loginRequest);
        return ResponseEntity.ok (loginResponse);
    }

    @GetMapping (value = BLOCK_USER)
    public ResponseEntity blockUser (@RequestParam String email) {
        authService.blockUser (email);
        return ResponseEntity.ok ().build ();
    }

    @PostMapping (value = GENERATE_NEW_ACCESS_TOKEN)
    public ResponseEntity<JwtRefreshResponse> getNewAccessToken (@RequestBody JwtRefreshRequest jwtRefreshRequest) {
        JwtRefreshResponse jwtResponse = jwtTokenHelper.getAccessToken (jwtRefreshRequest);
        return ResponseEntity.ok ().body (jwtResponse);
    }

    @PostMapping (value = GENERATE_NEW_REFRESH_TOKEN)
    public ResponseEntity<JwtRefreshResponse> getNewRefreshToken (@RequestBody JwtRefreshRequest jwtRefreshRequest) {
        JwtRefreshResponse jwtResponse = jwtTokenHelper.getRefreshToken (jwtRefreshRequest);
        return ResponseEntity.ok ().body (jwtResponse);
    }
}
