package com.keiko.userservice.util;

import com.keiko.userservice.dto.model.role.RoleDto;
import com.keiko.userservice.dto.model.user.UserDto;
import com.keiko.userservice.entity.Role;
import com.keiko.userservice.entity.User;

public class TestData {

    private static final String USER_EMAIL = "admin@gmail.com";
    private static final String USER_PASSWORD = "";
    //private static final String USER_PASSWORD = BCrypt.hashpw ("500290", BCrypt.gensalt ());
    private static final String USER_NAME = "test";
    private static final String ROLE_NAME = "ADMIN";

    public static User testUser () {
        User user = new User ();
        user.setEmail (USER_EMAIL);
        user.setPassword (USER_PASSWORD);
        user.setName (USER_NAME);
        return user;
    }

    public static UserDto testUserDto () {
        UserDto dto = new UserDto ();
        dto.setEmail (USER_EMAIL);
        dto.setPassword (USER_PASSWORD);
        dto.setName (USER_NAME);
        return dto;
    }

    public static Role testRole () {
        Role role = new Role ();
        role.setName (ROLE_NAME);
        return role;
    }

    public static RoleDto testRoleDto () {
        RoleDto dto = new RoleDto ();
        dto.setName (ROLE_NAME);
        return dto;
    }
}
