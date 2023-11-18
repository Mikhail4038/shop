package com.keiko.authservice.service;

import com.keiko.authservice.entity.User;
import com.keiko.authservice.request.LoginRequest;
import com.keiko.authservice.response.LoginResponse;
import com.keiko.authservice.validation.PasswordMatches;
import lombok.NonNull;

public interface AuthService {

    @PasswordMatches
    void registration (@NonNull User user);

    void confirmRegistration (String token);

    LoginResponse login (@NonNull LoginRequest loginRequest);

    void blockUser (String email);
}
