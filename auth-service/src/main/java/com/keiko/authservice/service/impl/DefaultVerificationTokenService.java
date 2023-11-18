package com.keiko.authservice.service.impl;

import com.keiko.authservice.entity.VerificationToken;
import com.keiko.authservice.exception.model.VerificationTokenProcessException;
import com.keiko.authservice.repository.VerificationTokenRepository;
import com.keiko.authservice.service.VerificationTokenService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DefaultVerificationTokenService implements VerificationTokenService {

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Override
    public void save (@NonNull VerificationToken verificationToken) {
        verificationTokenRepository.save (verificationToken);
    }

    @Override
    public VerificationToken findByToken (String token) {
        return verificationTokenRepository.findByToken (token)
                .orElseThrow (() -> new VerificationTokenProcessException (
                        String.format ("VerificationToken: %s not found", token)));
    }

    @Override
    public void deleteByToken (String token) {
        verificationTokenRepository.deleteByToken (token);
    }

    @Override
    public void deleteExpiredToken (LocalDateTime date) {
        verificationTokenRepository.deleteByExpiryDateLessThan (date);
    }
}
