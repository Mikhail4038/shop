package com.keiko.productservice.service.product;

import com.keiko.productservice.entity.Product;

import java.util.List;

public interface ProductRatingService {
    List<Product> findProductsRatingLessThan (Float rating, Boolean sortByAscend);

    List<Product> findProductsRatingMoreThan (Float rating, Boolean sortByAscend);

    List<Product> findProductsRatingRange (Float minRating, Float maxRating, Boolean sortByAscend);
}
