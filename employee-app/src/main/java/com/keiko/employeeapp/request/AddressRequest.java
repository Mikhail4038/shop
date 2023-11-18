package com.keiko.employeeapp.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressRequest {

    private String city;
    private String state;
    private Long employeeId;
}
