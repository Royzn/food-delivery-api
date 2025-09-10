package com.example.food_delivery_api.repository;

import com.example.food_delivery_api.entity.CourierEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourierRepository extends JpaRepository<CourierEntity, Long> {
}
