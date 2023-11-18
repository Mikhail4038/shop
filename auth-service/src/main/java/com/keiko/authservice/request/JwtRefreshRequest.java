package com.keiko.authservice.request;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class JwtRefreshRequest {
    private String refreshToken;
}
