package com.keiko.userservice.service;

import com.keiko.userservice.entity.User;
import com.keiko.userservice.request.UserRolesRequest;
import lombok.NonNull;

import java.util.List;

public interface UserService {
    User findByEmail (String email);

    List<User> findByRole (String role);

    List<User> findNotEnabled ();

    void deleteByEmail (String email);

    void deleteAll (List<User> users);

    void addRoles (@NonNull UserRolesRequest addRolesRequest);

    void deleteRoles (@NonNull UserRolesRequest deleteRolesRequest);
}
