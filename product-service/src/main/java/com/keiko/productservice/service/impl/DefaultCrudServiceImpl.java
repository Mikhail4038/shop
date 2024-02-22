package com.keiko.productservice.service.impl;

import com.keiko.commonservice.service.DefaultCrudService;
import com.keiko.productservice.entity.BaseEntity;
import com.keiko.productservice.repository.DefaultCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultCrudServiceImpl<E extends BaseEntity> implements DefaultCrudService<E> {

    @Autowired
    private DefaultCrudRepository<E> defaultCrudRepository;

    @Override
    public void save (E entity) {
        defaultCrudRepository.save (entity);
    }

    @Override
    public E fetchBy (Long id) {
        return defaultCrudRepository.findById (id).orElseThrow ();
    }

    @Override
    public List<E> fetchAll () {
        return defaultCrudRepository.findAll ();
    }

    @Override
    public void delete (Long id) {
        defaultCrudRepository.deleteById (id);
    }
}
