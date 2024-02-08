package com.keiko.notificationservice.entity.resources;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeliveryAddress {
    private String street;
    private String house;
    private String city;
    private String country;
    private String zipCode;
}
