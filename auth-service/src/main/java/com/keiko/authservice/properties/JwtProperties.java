package com.keiko.authservice.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties (prefix = "jwt")
@PropertySource ("classpath:jwt.properties")
@Getter
@Setter
public class JwtProperties {
    private String accessSecret;
    private String refreshSecret;
    private Long validityPeriodAccessToken;
    private Long validityPeriodRefreshToken;
}
