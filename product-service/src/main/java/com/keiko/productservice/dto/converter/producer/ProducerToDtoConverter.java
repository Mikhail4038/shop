package com.keiko.productservice.dto.converter.producer;

import com.keiko.productservice.dto.converter.AbstractToDtoConverter;
import com.keiko.productservice.dto.model.producer.ProducerDto;
import com.keiko.productservice.entity.Producer;
import org.springframework.stereotype.Component;

@Component
public class ProducerToDtoConverter extends AbstractToDtoConverter<Producer, ProducerDto> {

    public ProducerToDtoConverter () {
        super (Producer.class, ProducerDto.class);
    }

    @Override
    public void mapSpecificFields (Producer entity, ProducerDto dto) {
    }
}
