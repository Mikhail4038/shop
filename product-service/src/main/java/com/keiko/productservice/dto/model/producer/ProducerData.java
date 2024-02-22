package com.keiko.productservice.dto.model.producer;

import com.keiko.commonservice.dto.model.BaseDto;
import com.keiko.commonservice.entity.resource.Address;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProducerData extends BaseDto {
    private String name;
    private Address producerAddress;
}
