package com.keiko.authservice.service.resources;

import com.keiko.commonservice.entity.resource.user.Role;
import com.keiko.commonservice.entity.resource.user.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.keiko.commonservice.constants.MicroServiceConstants.USER_SERVICE;
import static com.keiko.commonservice.constants.WebResourceKeyConstants.*;

@FeignClient (name = USER_SERVICE)
public interface UserService {

    @GetMapping (value = USER_BASE + IS_EXISTS_USER)
    Boolean isExists (@RequestParam String email);

    @GetMapping (value = ROLE_BASE + FETCH_BY)
    Role findRoleById (@RequestParam Long id);

    @PostMapping (value = USER_BASE + SAVE)
    void save (@RequestBody User user);

    @GetMapping (value = USER_BASE + FIND_USER_BY_EMAIL)
    User findByEmail (@RequestParam String email);

    @DeleteMapping (value = USER_BASE + DELETE_USER_BY_EMAIL)
    void deleteByEmail (@RequestParam String email);

    @GetMapping (value = USER_BASE + FIND_NOT_ENABLED_USERS)
    List<User> findNotEnabledUsers ();
}
