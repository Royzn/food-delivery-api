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
                .id(customer.getCustomerId())
                .name(customer.getName())
                .createdAt(customer.getCreatedAt())
                .build();
    }

    public GetCustomerOrderDetailResponse getCustomerOrderDetail(Long id){
        // Find Customer
        CustomerEntity customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
        // Map customer name
        GetCustomerOrderDetailResponse response = new GetCustomerOrderDetailResponse();
        response.setCustomerName(customer.getName());
        // Map orders
        List<GetCustomerOrderResponse> orderResponses = customer.getOrderList().stream().map(order -> {
            GetCustomerOrderResponse orderResponse = new GetCustomerOrderResponse();
            orderResponse.setCourierName(order.getCourier().getName());
            orderResponse.setRestaurantName(order.getRestaurant().getName());
            orderResponse.setOrderStatus(order.getStatus());
            // Map order items
            List<GetCustomerOrderItemResponse> itemResponses = order.getOrderItemList().stream().map(item -> {
                GetCustomerOrderItemResponse itemResponse = new GetCustomerOrderItemResponse();
                itemResponse.setQuantity(item.getQuantity());
                itemResponse.setMenuName(item.getMenu().getName());
                itemResponse.setMenuPrice(item.getMenu().getPrice());
                return itemResponse;
            }).collect(Collectors.toList());
            orderResponse.setOrderItemList(itemResponses);
            return orderResponse;
        }).collect(Collectors.toList());
        response.setOrderList(orderResponses);
        return response;
    }
}
