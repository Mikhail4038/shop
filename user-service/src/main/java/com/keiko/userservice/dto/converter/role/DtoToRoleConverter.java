package com.keiko.userservice.dto.converter.role;

import com.keiko.userservice.dto.converter.AbstractToEntityConverter;
import com.keiko.userservice.dto.model.role.RoleDto;
import com.keiko.userservice.entity.Role;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class DtoToRoleConverter
        extends AbstractToEntityConverter<RoleDto, Role> {

    public DtoToRoleConverter () {
        super (RoleDto.class, Role.class);
    }

    @PostConstruct
    public void setUpMapping () {
        getModelMapper ().createTypeMap (RoleDto.class, Role.class)
                .addMappings (mapper -> mapper.skip (Role::setUsers));
    }

    @Override
    public void mapSpecificFields (RoleDto dto, Role role) {
    }
}
