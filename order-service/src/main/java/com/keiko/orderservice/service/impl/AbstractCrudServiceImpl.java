package com.keiko.orderservice.service.impl;

import com.keiko.orderservice.entity.BaseEntity;
import com.keiko.orderservice.repository.AbstractCrudRepository;
import com.keiko.orderservice.service.AbstractCrudService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AbstractCrudServiceImpl<T extends BaseEntity>
        implements AbstractCrudService<T> {

    @Autowired
    private AbstractCrudRepository<T> crudRepository;

    @Override
    public void save (T entity) {
        crudRepository.save (entity);
    }

    @Override
    public T fetchBy (Long id) {
        return crudRepository.findById (id).orElseThrow ();
    }

    @Override
    public List<T> fetchAll () {
        return crudRepository.findAll ();
    }

    @Override
    public void delete (Long id) {
        crudRepository.deleteById (id);
    }
}
