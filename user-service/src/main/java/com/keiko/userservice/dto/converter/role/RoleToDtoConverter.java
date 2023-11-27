package com.keiko.userservice.dto.converter.role;

import com.keiko.userservice.dto.converter.AbstractToDtoConverter;
import com.keiko.userservice.dto.model.role.RoleDto;
import com.keiko.userservice.entity.Role;
import org.springframework.stereotype.Component;

@Component
public class RoleToDtoConverter
        extends AbstractToDtoConverter<Role, RoleDto> {

    public RoleToDtoConverter () {
        super (Role.class, RoleDto.class);
    }

    public void mapSpecificFields (Role role, RoleDto dto) {
    }
}
