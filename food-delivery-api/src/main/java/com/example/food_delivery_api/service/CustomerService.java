package com.example.food_delivery_api.service;

import com.example.food_delivery_api.dto.customer.*;
import com.example.food_delivery_api.entity.CustomerEntity;
import com.example.food_delivery_api.entity.OrderEntity;
import com.example.food_delivery_api.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public interface CustomerService {
    public CreateCustomerResponse createCustomer(CreateCustomerRequest request);
    public GetCustomerOrderDetailResponse getCustomerOrderDetail(Long id);
}
