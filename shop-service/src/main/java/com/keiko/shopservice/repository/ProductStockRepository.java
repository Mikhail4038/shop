package com.keiko.shopservice.repository;

import com.keiko.shopservice.entity.ProductStock;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ProductStockRepository
        extends DefaultCrudRepository<ProductStock>,
        JpaSpecificationExecutor<ProductStock> {

    List<ProductStock> findByEan (String ean);

    List<ProductStock> findAll (Specification<ProductStock> spec);
}
