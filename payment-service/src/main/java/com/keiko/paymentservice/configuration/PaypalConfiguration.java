package com.keiko.paymentservice.configuration;

import com.keiko.paymentservice.properties.PaypalProperties;
import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaypalConfiguration {

    @Autowired
    private PaypalProperties paypalProperties;

    @Bean
    public PayPalHttpClient getPaypalClient () {
        String clientId = paypalProperties.getClientId ();
        String clientSecret = paypalProperties.getClientSecret ();
        return new PayPalHttpClient (new PayPalEnvironment.Sandbox (clientId, clientSecret));
    }
}
