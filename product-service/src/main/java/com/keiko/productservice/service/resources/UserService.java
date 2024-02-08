package com.keiko.productservice.service.resources;

import com.keiko.productservice.entity.resources.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.keiko.productservice.constants.MicroServiceConstants.USER_SERVICE;
import static com.keiko.productservice.constants.WebResourceKeyConstants.FETCH_BY_ID;
import static com.keiko.productservice.constants.WebResourceKeyConstants.USER_BASE;

@Service
@FeignClient (name = USER_SERVICE)
public interface UserService {

    @GetMapping (value = USER_BASE + FETCH_BY_ID)
    User fetchBy (@RequestParam Long id);
}
