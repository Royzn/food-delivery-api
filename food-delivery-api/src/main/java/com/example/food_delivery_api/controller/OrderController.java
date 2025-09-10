package com.example.food_delivery_api.controller;

import com.example.food_delivery_api.dto.order.*;
import com.example.food_delivery_api.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    OrderService orderService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreateOrderResponse> createOrder(@Valid @RequestBody CreateOrderRequest req){
        return orderService.createOrder(req);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetOrderDetailResponse> getOrderDetail(@PathVariable Long id){
        return ResponseEntity.ok(orderService.getOrderDetail(id));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<UpdateOrderStatusResponse> updateOrderStatus(
            @PathVariable Long id,
            @RequestBody UpdateOrderStatusRequest request
    ) {
        return ResponseEntity.ok(orderService.updateOrderStatus(id, request));
    }
}
