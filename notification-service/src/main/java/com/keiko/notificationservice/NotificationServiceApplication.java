package com.keiko.notificationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;

@SpringBootApplication
public class NotificationServiceApplication {

    public static void main (String[] args) {
        SpringApplication.run (NotificationServiceApplication.class, args);
    }

    @Bean
    public StringJsonMessageConverter jsonConverter () {
        return new StringJsonMessageConverter ();
    }
}
