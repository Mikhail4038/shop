package com.keiko.zuulproxy.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class User {
    private Long id;
    private String email;
    private String password;
    private String name;
    private boolean enabled;
    private Set<Role> roles;
}
