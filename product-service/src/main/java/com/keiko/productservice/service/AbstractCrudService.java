package com.keiko.productservice.service;

import java.util.List;

public interface AbstractCrudService<E> {

    void save (E entity);

    E fetchBy (Long id);

    List<E> fetchAll ();

    void delete (Long id);
}
