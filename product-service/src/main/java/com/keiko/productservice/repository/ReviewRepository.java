package com.keiko.productservice.repository;

import com.keiko.productservice.entity.Review;
import com.keiko.productservice.repository.crud.CrudRepository;

import java.util.List;

public interface ReviewRepository extends CrudRepository<Review> {

    List<Review> findByUser (Long userId);

    List<Review> findByProduct_id (Long productId);
}
