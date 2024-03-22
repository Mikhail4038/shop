package com.keiko.userservice.service;

import com.keiko.userservice.entity.Role;

import java.util.List;

public interface RoleService {
    Role findByName (String name);

    List<Role> getUserRoles (String email);
}
