package com.keiko.authservice.repository.specs;

import com.keiko.authservice.entity.VerificationToken;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class VerificationTokenSpecs {

    public static Specification<VerificationToken> isExpired () {
        return (root, query, builder) -> {
            LocalDateTime now = LocalDateTime.now ();
            return builder.lessThan (root.get ("expiryDate"), now);
        };
    }
}
