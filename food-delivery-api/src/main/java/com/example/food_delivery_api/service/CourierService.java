package com.example.food_delivery_api.service;

import com.example.food_delivery_api.dto.courier.*;

public interface CourierService {
    CreateCourierResponse createCourier(CreateCourierRequest request);
    GetCourierOrderDetailResponse getCourierOrderDetail(Long id);
}
