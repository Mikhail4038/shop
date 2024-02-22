package com.keiko.commonservice.entity.resource.product;

import com.keiko.commonservice.entity.resource.Address;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Producer {
    private String name;
    private Address producerAddress;
}
