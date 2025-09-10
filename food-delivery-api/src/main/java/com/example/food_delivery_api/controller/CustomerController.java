package com.example.food_delivery_api.controller;

import com.example.food_delivery_api.dto.customer.CreateCustomerRequest;
import com.example.food_delivery_api.dto.customer.CreateCustomerResponse;
import com.example.food_delivery_api.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public ResponseEntity<CreateCustomerResponse> createUser(@Valid @RequestBody CreateCustomerRequest request){
        return ResponseEntity.ok(customerService.createCustomer(request));
    }
}
