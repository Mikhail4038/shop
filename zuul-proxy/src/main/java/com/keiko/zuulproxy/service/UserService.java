package com.keiko.zuulproxy.service;

import com.keiko.zuulproxy.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.keiko.zuulproxy.constants.MicroServiceConstants.FIND_USER_BY_EMAIL;
import static com.keiko.zuulproxy.constants.MicroServiceConstants.USER_MICROSERVICE;

// TODO!!!
@FeignClient (name = USER_MICROSERVICE)
public interface UserService {

    @GetMapping (value = FIND_USER_BY_EMAIL)
    User findUserByEmail (@RequestParam String email);
}
