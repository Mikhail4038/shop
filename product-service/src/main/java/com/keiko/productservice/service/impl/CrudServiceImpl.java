package com.keiko.productservice.service.impl;

import com.keiko.productservice.entity.BaseEntity;
import com.keiko.productservice.repository.crud.CrudRepository;
import com.keiko.productservice.service.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CrudServiceImpl<E extends BaseEntity> implements CrudService<E> {

    @Autowired
    private CrudRepository<E> crudRepository;

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
