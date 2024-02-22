package com.keiko.commonservice.repository;

import com.keiko.commonservice.entity.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DefaultCrudRepository<E extends BaseEntity>
        extends JpaRepository<E, Long> {
}
