package com.example.food_delivery_api.controller;

import com.example.food_delivery_api.dto.customer.CreateCustomerRequest;
import com.example.food_delivery_api.dto.customer.CreateCustomerResponse;
import com.example.food_delivery_api.dto.customer.GetCustomerOrderDetailResponse;
import com.example.food_delivery_api.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<CreateCustomerResponse> createUser(@Valid @RequestBody CreateCustomerRequest request){
        return ResponseEntity.ok(customerService.createCustomer(request));
    }

    @GetMapping("/{id}/orders")
    public ResponseEntity<GetCustomerOrderDetailResponse> getCustomerOrderDetail(@PathVariable Long id){
        return ResponseEntity.ok(customerService.getCustomerOrderDetail(id));
    }
}
