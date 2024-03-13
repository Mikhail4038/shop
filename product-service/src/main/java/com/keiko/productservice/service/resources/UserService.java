package com.keiko.productservice.service.resources;

import com.keiko.commonservice.entity.resource.user.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.keiko.commonservice.constants.MicroServiceConstants.USER_SERVICE;
import static com.keiko.commonservice.constants.WebResourceKeyConstants.USER_BASE;
import static com.keiko.productservice.constants.WebResourceKeyConstants.FETCH_BY_ID;

@Service
@FeignClient (name = USER_SERVICE)
public interface UserService {

    @GetMapping (value = USER_BASE + FETCH_BY_ID)
    User fetchBy (@RequestParam Long id);
}
