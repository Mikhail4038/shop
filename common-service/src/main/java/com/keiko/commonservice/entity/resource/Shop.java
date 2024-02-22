package com.keiko.commonservice.entity.resource;

import com.keiko.commonservice.entity.resource.Address;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Shop {
    private Long id;
    private Address shopAddress;
}
