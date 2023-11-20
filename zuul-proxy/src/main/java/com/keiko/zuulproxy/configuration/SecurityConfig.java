package com.keiko.zuulproxy.configuration;

import com.keiko.zuulproxy.filter.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @Override
    protected void configure (HttpSecurity http) throws Exception {
        http.httpBasic ().disable ()
                .csrf ().disable ()
                .authorizeRequests ().antMatchers ("/auth/**").permitAll ()
                .and ()
                .authorizeRequests ().antMatchers ("/user/**").hasAuthority ("ADMIN")
                .and ()
                .authorizeRequests ().anyRequest ().authenticated ()
                .and ().addFilterBefore (jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
