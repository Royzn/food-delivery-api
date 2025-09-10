package com.example.food_delivery_api.service;

import com.example.food_delivery_api.dto.customer.CreateCustomerRequest;
import com.example.food_delivery_api.dto.customer.CreateCustomerResponse;
import com.example.food_delivery_api.entity.CustomerEntity;
import com.example.food_delivery_api.entity.OrderEntity;
import com.example.food_delivery_api.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public CreateCustomerResponse createCustomer(CreateCustomerRequest request){
        CustomerEntity customer = CustomerEntity.builder()
                .name(request.getName())
                .createdAt(LocalDateTime.now())
                .build();
        return CreateCustomerResponse.builder()
                .name(customer.getName())
                .createdAt(customer.getCreatedAt())
                .build();
    }
}
