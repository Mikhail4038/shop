package com.keiko.zuulproxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
@EnableEurekaClient
@EnableAutoConfiguration (exclude = {DataSourceAutoConfiguration.class})
public class ZuulProxyApplication {

    public static void main (String[] args) {
        SpringApplication.run (ZuulProxyApplication.class, args);
    }

}
