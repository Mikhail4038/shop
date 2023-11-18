package com.keiko.userservice.service.impl;

import com.keiko.userservice.entity.Role;
import com.keiko.userservice.entity.User;
import com.keiko.userservice.exception.model.RoleNotFoundException;
import com.keiko.userservice.exception.model.UserNotFoundException;
import com.keiko.userservice.repository.RoleRepository;
import com.keiko.userservice.repository.UserRepository;
import com.keiko.userservice.request.ModifyUserRolesRequest;
import com.keiko.userservice.service.UserService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

import static java.util.Collections.EMPTY_SET;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toSet;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;

@Service
public class DefaultUserService extends DefaultCrudService<User>
        implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public User findByEmail (String email) {
        return userRepository.findByEmail (email)
                .orElseThrow (() -> new UserNotFoundException (
                        String.format ("User with email: %s not found", email)));
    }

    @Override
    public void deleteByEmail (String email) {
        userRepository.deleteByEmail (email);
    }

    @Override
    public List<User> findByRole (String role) {
        return userRepository.findByRoles_name (role);
    }

    @Override
    public void addRoles (@NonNull ModifyUserRolesRequest request) {
        Set<Role> rolesForAdd = getRolesFromRequest (request);

        if (isNotEmpty (rolesForAdd)) {
            User user = getUserFromRequest (request);
            Set<Role> presentedRoles = user.getRoles ();

            if (nonNull (presentedRoles)) {
                presentedRoles.addAll (rolesForAdd);
                user.setRoles (presentedRoles);
            } else {
                user.setRoles (rolesForAdd);
            }
            userRepository.save (user);
        }
    }

    @Override
    public void deleteRoles (@NonNull ModifyUserRolesRequest request) {
        User user = getUserFromRequest (request);
        Set<Role> actualRoles = user.getRoles ();

        if (nonNull (actualRoles)) {
            Set<Role> rolesForRemove = getRolesFromRequest (request);
            rolesForRemove.forEach (actualRoles::remove);
            user.setRoles (actualRoles);
            userRepository.save (user);
        }
    }

    @Override
    public void save (User user) {
        if (isNull (user.getId ())) {
            throw new UnsupportedOperationException ("User can be saved only from registration");
        }
        super.save (user);
    }

    private User getUserFromRequest (ModifyUserRolesRequest request) {
        final Long userId = request.getUserId ();
        User user = userRepository.findById (userId)
                .orElseThrow (() -> new UserNotFoundException (
                        String.format ("User with id: %s not found", userId)));
        return user;
    }

    private Set<Role> getRolesFromRequest (ModifyUserRolesRequest request) {
        Set<Long> rolesName = request.getRolesId ();
        if (nonNull (rolesName)) {
            Set<Role> roles = rolesName.stream ()
                    .map (id -> roleRepository.findById (id)
                            .orElseThrow (() -> new RoleNotFoundException (String.format
                                    ("Role with id: %s not found", id))))
                    .collect (toSet ());
            return roles;
        }
        return EMPTY_SET;
    }
}
