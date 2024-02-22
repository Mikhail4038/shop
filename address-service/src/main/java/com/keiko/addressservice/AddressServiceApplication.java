package com.keiko.addressservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication (exclude = {DataSourceAutoConfiguration.class})
public class AddressServiceApplication {

    public static void main (String[] args) {
        SpringApplication.run (AddressServiceApplication.class, args);
    }

}
