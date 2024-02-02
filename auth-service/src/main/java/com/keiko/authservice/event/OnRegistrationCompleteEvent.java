package com.keiko.authservice.event;

import com.keiko.authservice.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OnRegistrationCompleteEvent {

    private final User user;

    public OnRegistrationCompleteEvent (User user) {
        this.user = user;
    }
}
