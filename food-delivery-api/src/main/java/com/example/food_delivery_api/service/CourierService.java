package com.example.food_delivery_api.service;

import com.example.food_delivery_api.dto.courier.CreateCourierRequest;
import com.example.food_delivery_api.dto.courier.CreateCourierResponse;
import com.example.food_delivery_api.dto.customer.CreateCustomerRequest;
import com.example.food_delivery_api.dto.customer.CreateCustomerResponse;
import com.example.food_delivery_api.entity.CustomerEntity;
import com.example.food_delivery_api.repository.CourierRepository;
import com.example.food_delivery_api.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CourierService {

    @Autowired
    private CourierRepository courierRepository;

    public CreateCourierResponse createCustomer(CreateCourierRequest request){
        CustomerEntity customer = CustomerEntity.builder()
                .name(request.getName())
                .createdAt(LocalDateTime.now())
                .build();
        return CreateCourierResponse.builder()
                .name(customer.getName())
                .createdAt(customer.getCreatedAt())
                .build();
    }
}
