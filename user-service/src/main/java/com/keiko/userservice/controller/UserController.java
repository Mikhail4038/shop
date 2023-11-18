package com.keiko.userservice.controller;

import com.keiko.userservice.dto.model.user.UserDto;
import com.keiko.userservice.entity.User;
import com.keiko.userservice.request.ModifyUserRolesRequest;
import com.keiko.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.keiko.userservice.constants.WebResourceKeyConstants.*;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping (value = USER_BASE)
public class UserController
        extends DefaultCrudController<User, UserDto> {

    @Autowired
    private UserService userService;

    @GetMapping (value = FIND_USER_BY_EMAIL)
    public ResponseEntity<UserDto> findByEmail (@RequestParam String email) {
        User user = userService.findByEmail (email);
        UserDto dto = getToDtoConverter ().apply (user);
        return ResponseEntity.ok ().body (dto);
    }

    @DeleteMapping (value = DELETE_USER_BY_EMAIL)
    public ResponseEntity deleteByEmail (@RequestParam String email) {
        userService.deleteByEmail (email);
        return ResponseEntity.ok ().build ();
    }

    @GetMapping (value = FIND_USERS_BY_ROLE)
    public ResponseEntity<List<String>> findByRole (@RequestParam String role) {
        List<User> users = userService.findByRole (role);
        List<String> dto = users
                .stream ().map (User::getEmail)
                .collect (toList ());
        return ResponseEntity.ok (dto);
    }

    @PostMapping (value = ADD_ROLES)
    public ResponseEntity addRoles (@RequestBody ModifyUserRolesRequest request) {
        userService.addRoles (request);
        return ResponseEntity.ok ().build ();
    }

    @PostMapping (value = DELETE_ROLES)
    public ResponseEntity deleteRoles (@RequestBody ModifyUserRolesRequest request) {
        userService.deleteRoles (request);
        return ResponseEntity.ok ().build ();
    }
}
