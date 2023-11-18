package com.keiko.userservice.service.impl;

import com.keiko.userservice.entity.BaseEntity;
import com.keiko.userservice.repository.AbstractCrudRepository;
import com.keiko.userservice.service.AbstractCrudService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class DefaultCrudService<E extends BaseEntity>
        implements AbstractCrudService<E> {

    @Autowired
    private AbstractCrudRepository<E> crudRepository;

    @Override
    public void save (E entity) {
        crudRepository.save (entity);
    }

    @Override
    public E fetchBy (Long id) {
        return crudRepository.findById (id).orElseThrow ();
    }

    @Override
    public List<E> fetchAll () {
        return crudRepository.findAll ();
    }

    @Override
    public void delete (Long id) {
        E entity = this.fetchBy (id);
        crudRepository.delete (entity);
    }
}
