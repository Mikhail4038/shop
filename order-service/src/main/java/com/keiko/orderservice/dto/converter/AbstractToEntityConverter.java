package com.keiko.orderservice.dto.converter;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.function.Function;

public abstract class AbstractToEntityConverter<D, E> implements Function<D, E> {


    @Autowired
    private ModelMapper modelMapper;

    private Class<D> dtoClass;
    private Class<E> entityClass;

    public AbstractToEntityConverter (Class<D> dtoClass, Class<E> entityClass) {
        this.dtoClass = dtoClass;
        this.entityClass = entityClass;
    }

    @Override
    public E apply (D dto) {
        return modelMapper.map (dto, entityClass);
    }

    public Converter<D, E> converter = (context) -> {
        D dto = context.getSource ();
        E entity = context.getDestination ();
        mapSpecificFields (dto, entity);
        return entity;
    };

    protected abstract void mapSpecificFields (D dto, E entity);

    public ModelMapper getModelMapper () {
        return modelMapper;
    }
}
