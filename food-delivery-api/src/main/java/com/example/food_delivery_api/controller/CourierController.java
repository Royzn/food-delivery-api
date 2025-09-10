package com.example.food_delivery_api.controller;

import com.example.food_delivery_api.dto.courier.CreateCourierRequest;
import com.example.food_delivery_api.dto.courier.CreateCourierResponse;
import com.example.food_delivery_api.dto.customer.CreateCustomerRequest;
import com.example.food_delivery_api.dto.customer.CreateCustomerResponse;
import com.example.food_delivery_api.service.CourierService;
import com.example.food_delivery_api.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/couriers")
public class CourierController {
    @Autowired
    private CourierService courierService;

    @PostMapping
    public ResponseEntity<CreateCourierResponse> createUser(@Valid @RequestBody CreateCourierRequest request){
        return ResponseEntity.ok(courierService.createCustomer(request));
    }
}
