package com.keiko.commonservice.entity.resource.user;

import com.keiko.commonservice.entity.resource.Address;
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
    private Address userAddress;
}
