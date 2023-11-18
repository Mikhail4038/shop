package com.keiko.authservice.entity;

import com.keiko.authservice.event.listener.TimeEntityListener;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;

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

    @OneToOne (fetch = LAZY)
    private User user;
    private Timestamp expiryDate;

    public VerificationToken (String token) {
        this.token = token;
        this.expiryDate = calculateExpiryDate (EXPIRATION);
    }

    public VerificationToken (String token, User user) {
        this.token = token;
        this.user = user;
        this.expiryDate = calculateExpiryDate (EXPIRATION);
    }

    private Timestamp calculateExpiryDate (int expiryTimeInHours) {
        LocalDateTime expiryDate = LocalDateTime.now ().plusHours (expiryTimeInHours);
        Timestamp deadLine = Timestamp.valueOf (expiryDate);
        return deadLine;
    }
}
