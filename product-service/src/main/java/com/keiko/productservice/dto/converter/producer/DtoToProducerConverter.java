package com.keiko.productservice.dto.converter.producer;

import com.keiko.productservice.dto.converter.AbstractToEntityConverter;
import com.keiko.productservice.dto.model.producer.ProducerDto;
import com.keiko.productservice.entity.Producer;
import org.springframework.stereotype.Component;

@Component
public class DtoToProducerConverter extends AbstractToEntityConverter<ProducerDto, Producer> {

    public DtoToProducerConverter () {
        super (ProducerDto.class, Producer.class);
    }

    @Override
    protected void mapSpecificFields (ProducerDto dto, Producer entity) {

    }
}
