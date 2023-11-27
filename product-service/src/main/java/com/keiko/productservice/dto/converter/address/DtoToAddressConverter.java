package com.keiko.productservice.dto.converter.address;

import com.keiko.productservice.dto.converter.AbstractToEntityConverter;
import com.keiko.productservice.dto.model.address.AddressDto;
import com.keiko.productservice.entity.Address;
import org.springframework.stereotype.Component;

@Component
public class DtoToAddressConverter extends AbstractToEntityConverter<AddressDto, Address> {

    public DtoToAddressConverter () {
        super (AddressDto.class, Address.class);
    }

    @Override
    protected void mapSpecificFields (AddressDto dto, Address entity) {

    }
}
