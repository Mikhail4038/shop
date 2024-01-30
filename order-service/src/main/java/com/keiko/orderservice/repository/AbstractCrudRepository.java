package com.keiko.orderservice.repository;

import com.keiko.orderservice.entity.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AbstractCrudRepository<T extends BaseEntity>
        extends JpaRepository<T, Long> {
}
