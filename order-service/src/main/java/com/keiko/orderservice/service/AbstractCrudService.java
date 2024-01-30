package com.keiko.orderservice.service;

import com.keiko.orderservice.entity.BaseEntity;

import java.util.List;

public interface AbstractCrudService<T extends BaseEntity> {
    void save (T entity);

    T fetchBy (Long id);

    List<T> fetchAll ();

    void delete (Long id);
}
