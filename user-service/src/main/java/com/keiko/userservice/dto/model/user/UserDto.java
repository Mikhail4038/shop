package com.keiko.userservice.dto.model.user;

import com.keiko.userservice.dto.model.role.RoleData;
import com.keiko.userservice.entity.Address;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserDto extends UserData {
    private Address address;
    private String password;
    private Set<RoleData> roles;
}
