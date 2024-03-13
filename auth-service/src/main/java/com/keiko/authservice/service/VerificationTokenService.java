package com.keiko.authservice.service;

import com.keiko.authservice.entity.VerificationToken;
import lombok.NonNull;

import java.util.List;

public interface VerificationTokenService {

    void save (@NonNull VerificationToken verificationToken);

    VerificationToken findByToken (String token);

    void deleteByToken (String token);

    void deleteAll (List<VerificationToken> tokens);

    List<VerificationToken> findExpiredTokens ();
}
