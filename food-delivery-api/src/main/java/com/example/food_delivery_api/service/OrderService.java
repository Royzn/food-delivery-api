package com.example.food_delivery_api.service;

import com.example.food_delivery_api.dto.order.*;
import org.springframework.http.ResponseEntity;

public interface OrderService {
    CreateOrderResponse createOrder(CreateOrderRequest req);
    GetOrderDetailResponse getOrderDetail(Long id);
    UpdateOrderStatusResponse updateOrderStatus(Long id, UpdateOrderStatusRequest request);
}
