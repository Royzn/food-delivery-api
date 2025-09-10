package com.example.food_delivery_api.service;

import com.example.food_delivery_api.dto.courier.*;
import com.example.food_delivery_api.entity.CourierEntity;
import com.example.food_delivery_api.repository.CourierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourierService {

    @Autowired
    private CourierRepository courierRepository;

    public CreateCourierResponse createCourier(CreateCourierRequest request){
        CourierEntity courier = CourierEntity.builder()
                .name(request.getName())
                .createdAt(LocalDateTime.now())
                .build();
        return CreateCourierResponse.builder()
                .name(courier.getName())
                .createdAt(courier.getCreatedAt())
                .build();
    }

    public GetCourierOrderDetailResponse getCourierOrderDetail(Long id){
        // Find Customer
        CourierEntity courier = courierRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Courier not found"));
        // Map customer name
        GetCourierOrderDetailResponse response = new GetCourierOrderDetailResponse();
        response.setCourierName(courier.getName());
        // Map orders
        List<GetCourierOrderResponse> orderResponses = courier.getOrderList().stream().map(order -> {
            GetCourierOrderResponse orderResponse = new GetCourierOrderResponse();
            orderResponse.setCustomerName(order.getCustomer().getName());
            orderResponse.setRestaurantName(order.getRestaurant().getName());
            orderResponse.setOrderStatus(order.getStatus());
            // Map order items
            List<GetCourierOrderItemResponse> itemResponses = order.getOrderItemList().stream().map(item -> {
                GetCourierOrderItemResponse itemResponse = new GetCourierOrderItemResponse();
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
