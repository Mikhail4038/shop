package com.keiko.productservice.repository;

import com.keiko.productservice.entity.Review;

import java.util.List;

public interface ReviewRepository extends AbstractCrudRepository<Review> {

    List<Review> findByUserId (Long userId);

    List<Review> findByProduct_id (Long productId);
}
