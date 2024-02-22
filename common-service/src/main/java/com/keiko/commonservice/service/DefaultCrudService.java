package com.keiko.commonservice.service;

import java.util.List;

public interface DefaultCrudService<E> {
    void save (E entity);

    E fetchBy (Long id);

    List<E> fetchAll ();

    void delete (Long id);
}
