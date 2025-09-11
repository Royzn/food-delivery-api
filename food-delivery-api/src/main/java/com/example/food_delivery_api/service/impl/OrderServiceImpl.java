package com.example.food_delivery_api.service.impl;

import com.example.food_delivery_api.dto.order.*;
import com.example.food_delivery_api.entity.*;
import com.example.food_delivery_api.repository.*;
import com.example.food_delivery_api.service.OrderService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CustomerRepository customerRepository;
    private final RestaurantRepository restaurantRepository;
    private final CourierRepository courierRepository;
    private final MenuRepository menuRepository;

    @Override
    public CreateOrderResponse createOrder(CreateOrderRequest req){
        CustomerEntity customer = customerRepository.findById(req.getCustomerId()).orElse(null);
        if(customer == null) return null;

        RestaurantEntity r = restaurantRepository.findById(req.getRestaurantId()).orElse(null);
        if(r == null) return null;

        CourierEntity courier = courierRepository.findById(req.getCourierId()).orElse(null);
        if(courier == null) return null;

        if(r.getStatus().equals(RestaurantStatusEnum.CLOSE.toString())) return null;

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

        return CreateOrderResponse.builder()
                        .orderId(savedOrder.getOrderId())
                        .restaurantName(r.getName())
                        .courierName(courier.getName())
                        .status(savedOrder.getStatus())
                        .createdAt(savedOrder.getCreatedAt())
                        .build();
    }

    @Override
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
                .status(order.getStatus())
                .orderItemList(itemResponses)
                .build();
    }

    @Override
    public UpdateOrderStatusResponse updateOrderStatus(Long id, UpdateOrderStatusRequest request){
        // Find order
        OrderEntity order = orderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order ID not found"));
        // Enum validation from req
        OrderStatusEnum newStatus;
        try {
            newStatus = OrderStatusEnum.valueOf(request.getStatus().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid status value");
        }
        // Save
        order.setStatus(newStatus.toString());
        order.setUpdatedAt(LocalDateTime.now());
        orderRepository.save(order);
        // save
        return UpdateOrderStatusResponse.builder()
                .orderId(order.getOrderId())
                .customerName(order.getCustomer().getName())
                .courierName(order.getCourier().getName())
                .restaurantName(order.getRestaurant().getName())
                .status(order.getStatus())
                .build();
    }
}
