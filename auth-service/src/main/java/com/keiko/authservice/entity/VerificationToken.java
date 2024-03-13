package com.keiko.authservice.entity;

import com.keiko.commonservice.entity.BaseEntity;
import com.keiko.commonservice.listener.TimeEntityListener;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table (name = "t_verificationToken")
@Getter
@Setter
@NoArgsConstructor
@EntityListeners (TimeEntityListener.class)
public class VerificationToken extends BaseEntity {
    private static final int EXPIRATION = 24;

    @Column (nullable = false)
    private String token;

    @Column (unique = true)
    private String email;

    private LocalDateTime expiryDate;

    public VerificationToken (String token, String email) {
        this.token = token;
        this.email = email;
        this.expiryDate = calculateExpiryDate (EXPIRATION);
    }

    private LocalDateTime calculateExpiryDate (int expiryTimeInHours) {
        LocalDateTime expiryDate = LocalDateTime.now ().plusHours (expiryTimeInHours);
        return expiryDate;
    }
}
