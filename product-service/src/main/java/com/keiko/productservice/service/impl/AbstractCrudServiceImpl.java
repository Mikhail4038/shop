package com.keiko.productservice.service.impl;

import com.keiko.productservice.entity.BaseEntity;
import com.keiko.productservice.repository.AbstractCrudRepository;
import com.keiko.productservice.service.AbstractCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AbstractCrudServiceImpl<E extends BaseEntity> implements AbstractCrudService<E> {

    @Autowired
    private AbstractCrudRepository<E> abstractCrudRepository;

    @Override
    public void save (E entity) {
        abstractCrudRepository.save (entity);
    }

    @Override
    public E fetchBy (Long id) {
        return abstractCrudRepository.findById (id).orElseThrow ();
    }

    @Override
    public List<E> fetchAll () {
        return abstractCrudRepository.findAll ();
    }

    @Override
    public void delete (Long id) {
        abstractCrudRepository.deleteById (id);
    }
}
