package com.keiko.stockservice.repository;

import com.keiko.stockservice.entity.ProductStock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductStockRepository
        extends JpaRepository<ProductStock, Long> {

    List<ProductStock> findByEan (String ean);
}
