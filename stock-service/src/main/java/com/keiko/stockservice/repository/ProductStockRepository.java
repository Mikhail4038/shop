package com.keiko.stockservice.repository;

import com.keiko.stockservice.entity.ProductStock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductStockRepository
        extends JpaRepository<ProductStock, Long> {

    Optional<ProductStock> findByEan (String ean);
}
