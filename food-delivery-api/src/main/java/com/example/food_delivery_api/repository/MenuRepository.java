package com.example.food_delivery_api.repository;

import com.example.food_delivery_api.entity.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<MenuEntity, Long> {
}
