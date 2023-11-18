package com.keiko.userservice.dto.model.user;

import com.keiko.userservice.dto.model.BaseDto;
import com.keiko.userservice.dto.model.role.RoleData;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserDto extends BaseDto {
    private String email;
    private String password;
    private String name;
    private boolean enabled;
    private Set<RoleData> roles;
}
