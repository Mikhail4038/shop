package com.keiko.authservice.repository;

import com.keiko.authservice.entity.VerificationToken;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;


public interface VerificationTokenRepository
        extends JpaRepository<VerificationToken, Long>, JpaSpecificationExecutor<VerificationToken> {

    List<VerificationToken> findAll (Specification<VerificationToken> spec);

    Optional<VerificationToken> findByToken (String token);

    void deleteByToken (String token);

}
