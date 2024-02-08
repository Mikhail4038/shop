package com.keiko.orderservice.entity.resources;

import com.keiko.orderservice.entity.Address;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private String email;
    private String name;
    private Address address;

}
