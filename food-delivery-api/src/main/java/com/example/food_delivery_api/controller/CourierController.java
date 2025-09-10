package com.example.food_delivery_api.controller;

import com.example.food_delivery_api.dto.courier.CreateCourierRequest;
import com.example.food_delivery_api.dto.courier.CreateCourierResponse;
import com.example.food_delivery_api.dto.courier.GetCourierOrderDetailResponse;
import com.example.food_delivery_api.service.CourierService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/couriers")
public class CourierController {
    @Autowired
    private CourierService courierService;

    @PostMapping
    public ResponseEntity<CreateCourierResponse> createCourier(@Valid @RequestBody CreateCourierRequest request){
        return ResponseEntity.ok(courierService.createCourier(request));
    }

    @GetMapping("/{id}/deliveries")
    public ResponseEntity<GetCourierOrderDetailResponse> getCourierOrderDetail(@PathVariable Long id){
        return ResponseEntity.ok(courierService.getCourierOrderDetail(id));
    }
}
