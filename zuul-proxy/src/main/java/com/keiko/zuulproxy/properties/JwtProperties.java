package com.keiko.zuulproxy.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties
//@PropertySource ("classpath:jwt.properties")
@Getter
@Setter
public class JwtProperties {
    private String jwtAccessSecret;
    private String refreshSecret;

    public JwtProperties () {
        System.out.println ("QQQQ");
    }
}
