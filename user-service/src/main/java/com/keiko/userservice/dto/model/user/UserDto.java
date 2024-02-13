package com.keiko.userservice.dto.model.user;

import com.keiko.userservice.dto.model.role.RoleData;
import com.keiko.userservice.entity.UserAddress;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserDto extends UserData {
    private UserAddress userAddress;
    private String password;
    private Set<RoleData> roles;
}
