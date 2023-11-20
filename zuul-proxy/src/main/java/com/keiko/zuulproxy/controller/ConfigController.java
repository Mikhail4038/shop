package com.keiko.zuulproxy.controller;

import com.keiko.zuulproxy.properties.JwtProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigController {

    @Autowired
    private JwtProperties jwtProperties;

    @GetMapping ("/prop")
    public JwtProperties prop () {
        return jwtProperties;
    }
}
