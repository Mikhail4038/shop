package com.keiko.authservice.repository;

import com.keiko.authservice.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;


public interface VerificationTokenRepository
        extends JpaRepository<VerificationToken, Long> {

    Optional<VerificationToken> findByToken (String token);

    void deleteByToken (String token);

    void deleteByExpiryDateLessThan (LocalDateTime date);
}
