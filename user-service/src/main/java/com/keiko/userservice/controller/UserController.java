package com.keiko.userservice.controller;

import com.keiko.commonservice.controller.DefaultCrudController;
import com.keiko.userservice.dto.model.user.UserDto;
import com.keiko.userservice.entity.User;
import com.keiko.userservice.exception.model.UserNotFoundException;
import com.keiko.userservice.request.UserRolesRequest;
import com.keiko.userservice.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.keiko.commonservice.constants.WebResourceKeyConstants.*;
import static com.keiko.userservice.constants.WebResourceKeyConstants.*;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping (value = USER_BASE)
@Tag (name = "Users API")
public class UserController
        extends DefaultCrudController<User, UserDto> {

    @Autowired
    private UserService userService;

    @GetMapping (value = IS_EXISTS_USER)
    public ResponseEntity<Boolean> isExists (@RequestParam String email) {
        Boolean isExists;
        try {
            userService.findByEmail (email);
            isExists = true;
        } catch (UserNotFoundException ex) {
            isExists = false;
        }
        return ResponseEntity.ok ().body (isExists);
    }


    @GetMapping (value = FIND_USER_BY_EMAIL)
    public ResponseEntity<UserDto> findByEmail (@RequestParam String email) {
        User user = userService.findByEmail (email);
        UserDto dto = getToDtoConverter ().apply (user);
        return ResponseEntity.ok ().body (dto);
    }

    @GetMapping (value = FIND_USERS_BY_ROLE)
    public ResponseEntity<List<String>> findByRole (@RequestParam String role) {
        List<User> users = userService.findByRole (role);
        List<String> dto = users
                .stream ().map (User::getEmail)
                .collect (toList ());
        return ResponseEntity.ok (dto);
    }

    @GetMapping (value = FIND_NOT_ENABLED_USERS)
    public ResponseEntity<List<UserDto>> findNotEnabled () {
        List<User> users = userService.findNotEnabled ();
        List<UserDto> dto = users
                .stream ().map (getToDtoConverter ()::apply)
                .collect (toList ());
        return ResponseEntity.ok (dto);
    }

    @DeleteMapping (value = DELETE_USER_BY_EMAIL)
    public ResponseEntity deleteByEmail (@RequestParam String email) {
        userService.deleteByEmail (email);
        return ResponseEntity.ok ().build ();
    }

    @DeleteMapping (value = DELETE_ALL)
    public ResponseEntity deleteAll (@RequestBody List<User> users) {
        userService.deleteAll (users);
        return ResponseEntity.ok ().build ();
    }

    @PostMapping (value = ADD_ROLES)
    public ResponseEntity addRoles (@RequestBody UserRolesRequest addRolesRequest) {
        userService.addRoles (addRolesRequest);
        return ResponseEntity.ok ().build ();
    }

    @PostMapping (value = DELETE_ROLES)
    public ResponseEntity deleteRoles (@RequestBody UserRolesRequest deleteRolesRequest) {
        userService.deleteRoles (deleteRolesRequest);
        return ResponseEntity.ok ().build ();
    }
}
