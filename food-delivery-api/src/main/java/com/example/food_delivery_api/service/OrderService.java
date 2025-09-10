package com.example.food_delivery_api.service;

import com.example.food_delivery_api.dto.customer.GetCustomerOrderDetailResponse;
import com.example.food_delivery_api.dto.order.*;
import com.example.food_delivery_api.entity.*;
import com.example.food_delivery_api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    CourierRepository courierRepository;

    @Autowired
    MenuRepository menuRepository;

    public ResponseEntity<CreateOrderResponse> createOrder(CreateOrderRequest req){
        CustomerEntity customer = customerRepository.findById(req.getCustomerId()).orElse(null);
        if(customer == null) return ResponseEntity.notFound().build();

        RestaurantEntity r = restaurantRepository.findById(req.getRestaurantId()).orElse(null);
        if(r == null) return ResponseEntity.notFound().build();

        CourierEntity courier = courierRepository.findById(req.getCourierId()).orElse(null);
        if(courier == null) return ResponseEntity.notFound().build();

        if(r.getStatus().equals(RestaurantStatusEnum.CLOSE.toString())) return ResponseEntity.badRequest().build();

        OrderEntity order = OrderEntity.builder()
                .createdAt(LocalDateTime.now())
                .courier(courier)
                .customer(customer)
                .restaurant(r)
                .status(OrderStatusEnum.PENDING.toString())
                .build();

        OrderEntity savedOrder = orderRepository.save(order);

        List<CreateOrderItemRequest> orderItems = req.getOrderItemList();

        List<Long> menuIdList = orderItems.stream()
                .map(CreateOrderItemRequest::getMenuId)
                .toList();

        Map<Long, MenuEntity> menuEntityMap = menuRepository.findAllById(menuIdList)
                .stream()
                .collect(Collectors.toMap(MenuEntity::getMenuId, Function.identity()));

        if (menuEntityMap.size() != menuIdList.size()) {
            throw new IllegalArgumentException("One or more menu IDs are invalid.");
        }

        List<OrderItemEntity> orderItemEntities = orderItems.stream()
                .map(oi -> OrderItemEntity.builder()
                        .createdAt(LocalDateTime.now())
                        .order(savedOrder)
                        .menu(menuEntityMap.get(oi.getMenuId()))
                        .quantity(oi.getQuantity())
                        .build())
                .toList();

        orderItemRepository.saveAll(orderItemEntities);

        return ResponseEntity.ok(
                CreateOrderResponse.builder()
                        .orderId(savedOrder.getOrderId())
                        .restaurantName(r.getName())
                        .courierName(courier.getName())
                        .status(savedOrder.getStatus())
                        .createdAt(savedOrder.getCreatedAt())
                        .build()
        );
    }

    public GetOrderDetailResponse getOrderDetail(Long id) {
        // Find order
        OrderEntity order = orderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order ID not found"));
        // Map order item
        List<GetOrderItemResponse> itemResponses = order.getOrderItemList().stream()
                .map(item -> GetOrderItemResponse.builder()
                        .quantity(item.getQuantity())
                        .menuName(item.getMenu().getName())
                        .menuPrice(item.getMenu().getPrice())
                        .build())
                .collect(Collectors.toList());
        // Return DTO
        return GetOrderDetailResponse.builder()
                .customerName(order.getCustomer().getName())
                .courierName(order.getCourier().getName())
                .restaurantName(order.getRestaurant().getName())
                .orderItemList(itemResponses)
                .build();
    }
}
