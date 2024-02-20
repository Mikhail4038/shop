package com.keiko.orderservice.repository;

import com.keiko.orderservice.entity.Order;

import java.util.Optional;

public interface OrderRepository extends AbstractCrudRepository<Order> {
    Optional<Order> findByPayId (String payId);
}
