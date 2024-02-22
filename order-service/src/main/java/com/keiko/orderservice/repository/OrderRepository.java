package com.keiko.orderservice.repository;

import com.keiko.commonservice.repository.DefaultCrudRepository;
import com.keiko.orderservice.entity.Order;
import com.keiko.orderservice.entity.OrderStatus;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends DefaultCrudRepository<Order> {
    Optional<Order> findByPayId (String payId);

    List<Order> findByOrderStatus (OrderStatus orderStatus);
}
