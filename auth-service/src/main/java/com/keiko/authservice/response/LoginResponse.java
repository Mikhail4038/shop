package com.keiko.authservice.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class LoginResponse {
    private final String type = "Bearer";
    private String accessToken;
    private String refreshToken;
}
