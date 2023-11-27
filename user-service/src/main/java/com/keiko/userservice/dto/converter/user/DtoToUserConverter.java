package com.keiko.userservice.dto.converter.user;

import com.keiko.userservice.dto.converter.AbstractToEntityConverter;
import com.keiko.userservice.dto.model.user.UserDto;
import com.keiko.userservice.entity.User;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class DtoToUserConverter
        extends AbstractToEntityConverter<UserDto, User> {

    public DtoToUserConverter () {
        super (UserDto.class, User.class);
    }

    @PostConstruct
    public void setUpMapping () {
    }

    public void mapSpecificFields (UserDto dto, User user) {
    }
}
