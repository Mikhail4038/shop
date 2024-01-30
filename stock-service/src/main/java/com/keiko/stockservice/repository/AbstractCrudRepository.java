package com.keiko.stockservice.repository;

import com.keiko.stockservice.entity.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AbstractCrudRepository<T extends BaseEntity>
        extends JpaRepository<T, Long> {
}
