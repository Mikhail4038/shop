package com.keiko.stockservice.repository;

import com.keiko.stockservice.entity.ProductStock;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ProductStockRepository
        extends JpaRepository<ProductStock, Long>,
        JpaSpecificationExecutor<ProductStock> {

    List<ProductStock> findByEan (String ean);

    List<ProductStock> findAll (Specification<ProductStock> spec);
}
