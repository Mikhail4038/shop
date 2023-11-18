package com.keiko.userservice.util;

import com.keiko.userservice.dto.model.role.RoleDto;
import com.keiko.userservice.dto.model.user.UserDto;
import com.keiko.userservice.entity.Role;
import com.keiko.userservice.entity.User;
import org.springframework.security.crypto.bcrypt.BCrypt;

public class TestData {

    private static final String USER_EMAIL = "admin@gmail.com";
    private static final String USER_PASSWORD = BCrypt.hashpw ("500290", BCrypt.gensalt ());
    private static final String USER_NAME = "test";
    private static final String ROLE_NAME = "ADMIN";

    public static User createTestUser () {
        User user = new User ();
        user.setEmail (USER_EMAIL);
        user.setPassword (USER_PASSWORD);
        user.setName (USER_NAME);
        return user;
    }

    public static UserDto createTestUserDto () {
        UserDto dto = new UserDto ();
        dto.setEmail (USER_EMAIL);
        dto.setPassword (USER_PASSWORD);
        dto.setName (USER_NAME);
        return dto;
    }

    public static Role createTestRole () {
        Role role = new Role ();
        role.setName (ROLE_NAME);
        return role;
    }

    public static RoleDto createTestRoleDto () {
        RoleDto dto = new RoleDto ();
        dto.setName (ROLE_NAME);
        return dto;
    }
}
