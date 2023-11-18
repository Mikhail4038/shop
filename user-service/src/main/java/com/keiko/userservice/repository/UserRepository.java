package com.keiko.userservice.repository;

import com.keiko.userservice.entity.User;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;
import java.util.Optional;

public interface UserRepository
        extends AbstractCrudRepository<User> {

    Optional<User> findByEmail (String email);

    @Modifying
    void deleteByEmail (String email);

    List<User> findByRoles_name (String name);

}
