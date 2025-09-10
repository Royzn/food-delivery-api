package com.example.food_delivery_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "couriers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourierEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courierId;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "courier", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderEntity> orderList;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

}
