package com.keiko.productservice.dto.converter;

import org.modelmapper.Conditions;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.function.Function;

public abstract class AbstractToDtoConverter<E, D> implements Function<E, D> {

    private Class<E> entityClass;
    private Class<D> dtoClass;

    public AbstractToDtoConverter (Class<E> entityClass, Class<D> dtoClass) {
        this.entityClass = entityClass;
        this.dtoClass = dtoClass;
    }

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public D apply (E e) {
        modelMapper.getConfiguration ().setPropertyCondition (Conditions.isNotNull ());
        return modelMapper.map (e, dtoClass);
    }

    // have opportunity add converter during set up mapping
    public Converter<E, D> converter = (context) -> {
        E entity = context.getSource ();
        D dto = context.getDestination ();
        mapSpecificFields (entity, dto);
        return dto;
    };

    public abstract void mapSpecificFields (E entity, D dto);

    public ModelMapper getModelMapper () {
        return modelMapper;
    }
}
