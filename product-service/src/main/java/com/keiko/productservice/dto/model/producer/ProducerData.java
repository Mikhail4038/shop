package com.keiko.productservice.dto.model.producer;

import com.keiko.productservice.dto.model.BaseDto;
import com.keiko.productservice.dto.model.address.AddressDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProducerData extends BaseDto {
    private String name;
    private AddressDto address;
}
