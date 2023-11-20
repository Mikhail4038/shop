package com.keiko.zuulproxy.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Getter
@Setter
public class Role implements GrantedAuthority {

    private String name;

    @Override
    public String getAuthority () {
        return name;
    }
}
