package com.keiko.userservice.dto.converter.user;

import com.keiko.userservice.dto.converter.AbstractToDtoConverter;
import com.keiko.userservice.dto.model.role.RoleData;
import com.keiko.userservice.dto.model.user.UserDto;
import com.keiko.userservice.entity.Role;
import com.keiko.userservice.entity.User;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.Set;

import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toSet;

@Component
public class UserToDtoConverter
        extends AbstractToDtoConverter<User, UserDto> {

    public UserToDtoConverter () {
        super (User.class, UserDto.class);
    }

    @PostConstruct
    public void setUpMapping () {
        getModelMapper ().createTypeMap (User.class, UserDto.class)
                .addMappings (mapper -> mapper.skip (UserDto::setPassword))
                .addMappings (mapper -> mapper.skip (UserDto::setRoles))
                .setPostConverter (converter);
    }

    public void mapSpecificFields (User user, UserDto dto) {
        Set<Role> roles = user.getRoles ();
        if (nonNull (roles)) {
            Set<RoleData> rolesData = roles.stream ()
                    .map (r -> getModelMapper ().map (r, RoleData.class))
                    .collect (toSet ());
            dto.setRoles (rolesData);
        }
    }
}
