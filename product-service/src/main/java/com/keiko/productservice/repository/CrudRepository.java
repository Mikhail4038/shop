package com.keiko.productservice.repository;

import com.keiko.productservice.entity.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CrudRepository<E extends BaseEntity>
        extends JpaRepository<E, Long> {
}
