package com.keiko.authservice.repository;

import com.keiko.authservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail (String email);

    void deleteByEmail (String email);

    List<User> findByEnabledIsAndVerificationToken_expiryDateLessThan (boolean enabled, LocalDateTime date);
}
