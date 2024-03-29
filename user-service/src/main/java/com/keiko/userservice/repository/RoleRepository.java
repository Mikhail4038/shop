package com.keiko.userservice.repository;

import com.keiko.commonservice.repository.DefaultCrudRepository;
import com.keiko.userservice.entity.Role;

import java.util.List;
import java.util.Optional;

public interface RoleRepository
        extends DefaultCrudRepository<Role> {

    Optional<Role> findByName (String name);

    List<Role> findByUsers_email (String email);
}
