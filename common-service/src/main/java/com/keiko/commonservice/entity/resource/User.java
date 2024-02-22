package com.keiko.commonservice.entity.resource;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private Long id;
    private String email;
    private String name;
    private Address userAddress;
}
