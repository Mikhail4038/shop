package com.keiko.userservice.repository;

import com.keiko.userservice.entity.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AbstractCrudRepository<E extends BaseEntity>
        extends JpaRepository<E, Long> {
}
