package com.keiko.userservice.service.impl;

import com.keiko.userservice.entity.Role;
import com.keiko.userservice.exception.model.RoleNotFoundException;
import com.keiko.userservice.repository.RoleRepository;
import com.keiko.userservice.service.RoleService;
import com.keiko.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultRoleService extends DefaultCrudService<Role>
        implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserService userService;

    @Override
    public Role findByName (String name) {
        return roleRepository.findByName (name)
                .orElseThrow (() -> new RoleNotFoundException (
                        String.format ("Role with name: %s not found", name)));
    }

    @Override
    public List<Role> getUserRoles (String user) {
        return roleRepository.findByUsers_email (user);
    }
}
