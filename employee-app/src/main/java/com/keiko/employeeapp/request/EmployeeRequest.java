package com.keiko.employeeapp.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeRequest {
    private String name;
    private String email;
    private Integer age;
    private AddressRequest addressRequest;
}
