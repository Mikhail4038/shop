package com.keiko.productservice.dto.model.address;

import com.keiko.commonservice.dto.model.BaseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDto extends BaseDto {
    private String street;
    private String house;
    private String city;
    private String country;
    private String postcode;
}
