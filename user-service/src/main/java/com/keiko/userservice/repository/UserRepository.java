package com.keiko.userservice.repository;

import com.keiko.commonservice.repository.DefaultCrudRepository;
import com.keiko.userservice.entity.User;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;
import java.util.Optional;

public interface UserRepository
        extends DefaultCrudRepository<User> {
    Optional<User> findByEmail (String email);

    Integer deleteByEmail (String email);

    List<User> findByRoles_name (String name);

    List<User> findByEnabledIs (boolean enabled);

}
