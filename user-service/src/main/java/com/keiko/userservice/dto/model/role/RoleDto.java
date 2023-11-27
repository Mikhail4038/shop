package com.keiko.userservice.dto.model.role;

import com.keiko.userservice.dto.model.user.UserData;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class RoleDto extends RoleData {
    private Set<UserData> users;
}
