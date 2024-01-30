package com.keiko.stockservice.service.impl;

import com.keiko.stockservice.entity.BaseEntity;
import com.keiko.stockservice.repository.AbstractCrudRepository;
import com.keiko.stockservice.service.AbstractCrudService;
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
    public T fetchById (Long id) {
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
