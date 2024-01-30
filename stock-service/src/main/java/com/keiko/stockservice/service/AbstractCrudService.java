package com.keiko.stockservice.service;

import java.util.List;

public interface AbstractCrudService<T> {
    void save (T entity);

    T fetchById (Long id);

    List<T> fetchAll ();

    void delete (Long id);

}
