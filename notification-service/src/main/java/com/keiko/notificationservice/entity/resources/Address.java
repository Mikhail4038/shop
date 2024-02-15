package com.keiko.notificationservice.entity.resources;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Address {
    private String street;
    private String house;
    private String city;
    private String country;
    private String postcode;

    @Override
    public String toString () {
        return country + ", " +
                city + ", " +
                street + ", " +
                house + ", " +
                postcode;
    }
}
