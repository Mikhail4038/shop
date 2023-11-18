package com.keiko.authservice.event;

import com.keiko.authservice.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class OnRegistrationCompleteEvent extends ApplicationEvent {

    private final User user;

    public OnRegistrationCompleteEvent (User user) {
        super (user);
        this.user = user;
    }
}
