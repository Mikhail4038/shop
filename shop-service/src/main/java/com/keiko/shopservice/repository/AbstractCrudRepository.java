package com.keiko.shopservice.repository;

import com.keiko.shopservice.entity.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AbstractCrudRepository<T extends BaseEntity>
        extends JpaRepository<T, Long> {
}
