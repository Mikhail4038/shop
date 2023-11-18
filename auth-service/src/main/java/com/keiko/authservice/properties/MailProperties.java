package com.keiko.authservice.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties
@PropertySource ("classpath:mail.properties")
@Getter
@Setter
public class MailProperties {
    private String supportEmail;
}
