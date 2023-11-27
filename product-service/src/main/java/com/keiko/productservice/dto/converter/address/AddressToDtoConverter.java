package com.keiko.productservice.dto.converter.address;

import com.keiko.productservice.dto.converter.AbstractToDtoConverter;
import com.keiko.productservice.dto.model.address.AddressDto;
import com.keiko.productservice.entity.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressToDtoConverter extends AbstractToDtoConverter<Address, AddressDto> {

    public AddressToDtoConverter () {
        super (Address.class, AddressDto.class);
    }

    @Override
    public void mapSpecificFields (Address entity, AddressDto dto) {
    }
}
