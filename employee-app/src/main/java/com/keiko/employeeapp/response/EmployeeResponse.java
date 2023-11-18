package com.keiko.employeeapp.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeResponse {

    private Long id;
    private String name;
    private String email;
    private Integer age;
    private AddressResponse addressResponse;
}
