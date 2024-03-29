package com.keiko.commonservice.dto.converter;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.function.Function;

public abstract class AbstractToDtoConverter<E, D>
        implements Function<E, D> {

    private final Class<E> entityClass;
    private final Class<D> dtoClass;

    @Autowired
    private ModelMapper modelMapper;

    public AbstractToDtoConverter (Class<E> entityClass, Class<D> dtoClass) {
        this.entityClass = entityClass;
        this.dtoClass = dtoClass;
    }

    @Override
    public D apply (E entity) {
        return modelMapper.map (entity, dtoClass);
    }

    public Converter<E, D> converter = context -> {
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
