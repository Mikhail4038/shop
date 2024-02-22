package com.keiko.shopservice.service.impl;

import com.keiko.commonservice.service.DefaultCrudService;
import com.keiko.shopservice.entity.BaseEntity;
import com.keiko.shopservice.repository.DefaultCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class DefaultCrudServiceImpl<T extends BaseEntity> implements DefaultCrudService<T> {

    @Autowired
    private DefaultCrudRepository<T> crudRepository;

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
