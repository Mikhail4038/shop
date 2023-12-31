package com.keiko.userservice.service;

import com.keiko.userservice.entity.User;
import com.keiko.userservice.request.ModifyUserRolesRequest;
import lombok.NonNull;

import java.util.List;

public interface UserService {
    User findByEmail (String email);

    void deleteByEmail (String email);

    List<User> findByRole (String role);

    void addRoles (@NonNull ModifyUserRolesRequest request);

    void deleteRoles (@NonNull ModifyUserRolesRequest request);
}
