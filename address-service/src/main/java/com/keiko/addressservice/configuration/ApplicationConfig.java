package com.keiko.addressservice.configuration;

import com.squareup.okhttp.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public OkHttpClient okHttpClient () {
        return new OkHttpClient ();
    }
}
