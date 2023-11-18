package com.keiko.userservice.controller;

import com.keiko.userservice.dto.model.role.RoleDto;
import com.keiko.userservice.entity.Role;
import com.keiko.userservice.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.keiko.userservice.constants.WebResourceKeyConstants.*;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping (value = ROLE_BASE)
public class RoleController
        extends DefaultCrudController<Role, RoleDto> {

    @Autowired
    private RoleService roleService;

    @GetMapping (value = FIND_ROLE_BY_NAME)
    public ResponseEntity<RoleDto> findByName (@RequestParam String name) {
        Role role = roleService.findByName (name);
        RoleDto dto = getToDtoConverter ().apply (role);
        return ResponseEntity.ok ().body (dto);
    }

    @GetMapping (value = GET_USER_ROLES)
    public ResponseEntity<List<String>> getAllRoles (@RequestParam String user) {
        List<Role> roles = roleService.getUserRoles (user);
        List<String> dto = roles
                .stream ().map (Role::getName)
                .collect (toList ());
        return ResponseEntity.ok (dto);
    }
}
