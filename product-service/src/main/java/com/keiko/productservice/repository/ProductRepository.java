package com.keiko.productservice.repository;

import com.keiko.productservice.entity.Product;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface ProductRepository
        extends AbstractCrudRepository<Product>, JpaSpecificationExecutor<Product> {

    List<Product> findAll (Specification<Product> spec);

    List<Product> findAll (Specification<Product> spec, Sort sort);

    Optional<Product> findByEan (String ean);
}
