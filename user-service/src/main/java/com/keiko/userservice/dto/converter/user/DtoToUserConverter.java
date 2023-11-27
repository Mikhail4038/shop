package com.keiko.userservice.dto.converter.user;

import com.keiko.userservice.dto.converter.AbstractToEntityConverter;
import com.keiko.userservice.dto.model.role.RoleData;
import com.keiko.userservice.dto.model.user.UserDto;
import com.keiko.userservice.entity.Role;
import com.keiko.userservice.entity.User;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Set;

import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toSet;

@Component
public class DtoToUserConverter
        extends AbstractToEntityConverter<UserDto, User> {

    public DtoToUserConverter () {
        super (UserDto.class, User.class);
    }

    @PostConstruct
    public void setUpMapping () {
        getModelMapper ().createTypeMap (UserDto.class, User.class)
                .addMappings (mapper -> mapper.skip (User::setRoles))
                .setPostConverter (converter);
    }

    public void mapSpecificFields (UserDto dto, User user) {
        Set<RoleData> rolesData = dto.getRoles ();
        if (nonNull (rolesData)) {
            ModelMapper roleMapper = new ModelMapper ();
            Set<Role> roles = rolesData.stream ()
                    .map (data -> roleMapper.map (data, Role.class))
                    .collect (toSet ());
            user.setRoles (roles);
        }
    }
}
