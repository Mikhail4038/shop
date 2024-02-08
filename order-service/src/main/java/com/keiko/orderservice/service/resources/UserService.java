package com.keiko.orderservice.service.resources;

import com.keiko.orderservice.entity.resources.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.keiko.orderservice.constants.MicroServiceConstants.USER_SERVICE;
import static com.keiko.orderservice.constants.WebResourceKeyConstants.FETCH_BY;
import static com.keiko.orderservice.constants.WebResourceKeyConstants.USER_BASE;

@Service
@FeignClient (name = USER_SERVICE)
public interface UserService {

    @GetMapping (value = USER_BASE + FETCH_BY)
    User fetchBy (@RequestParam Long id);
}
