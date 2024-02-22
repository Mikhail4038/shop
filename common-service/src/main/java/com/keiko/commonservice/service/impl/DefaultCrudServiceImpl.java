package com.keiko.commonservice.service.impl;

import com.keiko.commonservice.entity.BaseEntity;
import com.keiko.commonservice.repository.DefaultCrudRepository;
import com.keiko.commonservice.service.DefaultCrudService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class DefaultCrudServiceImpl<E extends BaseEntity>
        implements DefaultCrudService<E> {

    @Autowired
    private DefaultCrudRepository<E> crudRepository;

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
        crudRepository.deleteById (id);
    }
}
