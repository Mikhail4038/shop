package com.keiko.stockservice.service;

import com.keiko.stockservice.entity.ProductStock;

import java.util.List;

public interface ProductStockService {
    void save (ProductStock productStock);

    ProductStock fetchBy (Long id);

    List<ProductStock> fetchAll ();

    void delete (Long id);
}
