package com.keiko.paymentservice.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties (prefix = "paypal")
@Getter
@Setter
public class PaypalProperties {
    private String clientId;
    private String clientSecret;
}
