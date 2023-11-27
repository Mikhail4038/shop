package com.keiko.userservice.dto.converter.user;

import com.keiko.userservice.dto.converter.AbstractToDtoConverter;
import com.keiko.userservice.dto.model.user.UserDto;
import com.keiko.userservice.entity.User;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class UserToDtoConverter
        extends AbstractToDtoConverter<User, UserDto> {

    public UserToDtoConverter () {
        super (User.class, UserDto.class);
    }

    @PostConstruct
    public void setUpMapping () {
        getModelMapper ().createTypeMap (User.class, UserDto.class)
                .addMappings (mapper -> mapper.skip (UserDto::setPassword));
    }

    public void mapSpecificFields (User user, UserDto dto) {
    }
}
