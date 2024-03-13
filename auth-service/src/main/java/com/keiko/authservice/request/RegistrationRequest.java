package com.keiko.authservice.request;

import com.keiko.commonservice.entity.resource.Address;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationRequest {
    private String email;
    private String password;
    private String passwordConfirm;
    private String name;
    private Long roleId;
    private Address address;
}
