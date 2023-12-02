package com.keiko.productservice.service.product;

import com.keiko.productservice.entity.Product;

import java.util.List;

public interface ProductPriceService {

    List<Product> findProductsPriceLessThan (Double price, Boolean sortByAscend);

    List<Product> findProductsPriceMoreThan (Double price, Boolean sortByAscend);

    List<Product> findProductsPriceRange (Double minPrice, Double maxPrice, Boolean sortByAscend);
}
