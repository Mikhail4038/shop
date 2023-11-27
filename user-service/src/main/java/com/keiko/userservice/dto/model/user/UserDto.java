package com.keiko.userservice.dto.model.user;

import com.keiko.userservice.dto.model.role.RoleData;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

// TODO address!!!
@Getter
@Setter
public class UserDto extends UserData {
    private String password;
    private Set<RoleData> roles;
}
