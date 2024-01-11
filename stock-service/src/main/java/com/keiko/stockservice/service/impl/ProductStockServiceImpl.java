package com.keiko.stockservice.service.impl;

import com.keiko.stockservice.entity.ProductStock;
import com.keiko.stockservice.repository.ProductStockRepository;
import com.keiko.stockservice.service.ProductStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductStockServiceImpl implements ProductStockService {

    @Autowired
    private ProductStockRepository productStockRepository;

    @Override
    public void save (ProductStock productStock) {
        productStockRepository.save (productStock);
    }

    @Override
    public ProductStock fetchBy (Long id) {
        return productStockRepository.findById (id).orElseThrow ();
    }

    @Override
    public List<ProductStock> fetchAll () {
        return productStockRepository.findAll ();
    }

    @Override
    public void delete (Long id) {
        productStockRepository.deleteById (id);
    }
}