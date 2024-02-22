package com.keiko.shopservice.repository;

import com.keiko.shopservice.entity.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DefaultCrudRepository<T extends BaseEntity>
        extends JpaRepository<T, Long> {
}
