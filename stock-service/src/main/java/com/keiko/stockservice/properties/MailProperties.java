package com.keiko.stockservice.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties
@Getter
@Setter
public class MailProperties {
    private String supportEmail;
    private String adminEmail;
}
