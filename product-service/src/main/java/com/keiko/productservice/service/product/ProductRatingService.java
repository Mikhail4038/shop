package com.keiko.productservice.service.product;

import com.keiko.productservice.entity.Product;

import java.util.List;

public interface ProductRatingService {
    List<Product> findProductsRatingLessThan (Double averageAssessment, Boolean sortByAscend);

    List<Product> findProductsRatingMoreThan (Double averageAssessment, Boolean sortByAscend);

    List<Product> findProductsRatingRange (Float minAverageAssessment, Float maxAverageAssessment, Boolean sortByAscend);
}
