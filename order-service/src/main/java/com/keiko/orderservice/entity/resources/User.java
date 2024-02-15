package com.keiko.orderservice.entity.resources;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private String email;
    private String name;
    private Address userAddress;
}