package com.example.food_delivery_api.repository;

import com.example.food_delivery_api.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
}
