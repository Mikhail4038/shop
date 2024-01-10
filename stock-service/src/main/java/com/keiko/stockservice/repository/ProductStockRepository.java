package com.keiko.stockservice.repository;

import com.keiko.stockservice.entity.ProductStock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductStockRepository
        extends JpaRepository<ProductStock, Long> {
}
