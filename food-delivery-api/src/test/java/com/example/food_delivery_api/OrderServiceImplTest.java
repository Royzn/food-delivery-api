package com.example.food_delivery_api;

import com.example.food_delivery_api.dto.order.*;
import com.example.food_delivery_api.entity.*;
import com.example.food_delivery_api.repository.*;
import com.example.food_delivery_api.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderItemRepository orderItemRepository;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private RestaurantRepository restaurantRepository;
    @Mock
    private CourierRepository courierRepository;
    @Mock
    private MenuRepository menuRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createOrder_shouldReturnCreateOrderResponse_whenValidRequest() {
        LocalDateTime now = LocalDateTime.now();

        Long customerId = 1L;
        Long restaurantId = 2L;
        Long courierId = 3L;

        CreateOrderRequest req = CreateOrderRequest.builder()
                .customerId(customerId)
                .restaurantId(restaurantId)
                .courierId(courierId)
                .orderItemList(List.of(
                        CreateOrderItemRequest.builder().menuId(101L).quantity(2L).build(),
                        CreateOrderItemRequest.builder().menuId(102L).quantity(3L).build()
                ))
                .build();

        CustomerEntity customer = CustomerEntity.builder().customerId(customerId).build();
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        RestaurantEntity restaurant = RestaurantEntity.builder()
                .restaurantId(restaurantId)
                .name("Good Eats")
                .status(RestaurantStatusEnum.OPEN.toString())
                .build();
        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(restaurant));

        CourierEntity courier = CourierEntity.builder()
                .courierId(courierId)
                .name("John Doe")
                .build();
        when(courierRepository.findById(courierId)).thenReturn(Optional.of(courier));

        OrderEntity savedOrder = OrderEntity.builder()
                .orderId(555L)
                .createdAt(now)
                .courier(courier)
                .customer(customer)
                .restaurant(restaurant)
                .status(OrderStatusEnum.PENDING.toString())
                .build();
        when(orderRepository.save(any(OrderEntity.class))).thenReturn(savedOrder);

        MenuEntity menu1 = MenuEntity.builder().menuId(101L).build();
        MenuEntity menu2 = MenuEntity.builder().menuId(102L).build();
        when(menuRepository.findAllById(List.of(101L, 102L)))
                .thenReturn(List.of(menu1, menu2));

        when(orderItemRepository.saveAll(anyList())).thenAnswer(invocation -> invocation.getArgument(0));

        CreateOrderResponse response = orderService.createOrder(req);

        assertEquals(555L, response.getOrderId());
        assertEquals("Good Eats", response.getRestaurantName());
        assertEquals("John Doe", response.getCourierName());
        assertEquals(OrderStatusEnum.PENDING.toString(), response.getStatus());
        assertEquals(now, response.getCreatedAt());
    }

    @Test
    void testGetOrderDetail_Success() {
        // Prepare data
        MenuEntity menu = MenuEntity.builder()
                .name("Nasi Goreng")
                .price(25000D)
                .build();

        OrderItemEntity orderItem = OrderItemEntity.builder()
                .quantity(2L)
                .menu(menu)
                .build();

        CustomerEntity customer = CustomerEntity.builder()
                .name("Budi")
                .build();

        CourierEntity courier = CourierEntity.builder()
                .name("Andi")
                .build();

        RestaurantEntity restaurant = RestaurantEntity.builder()
                .name("Warung Makan")
                .build();

        OrderEntity order = OrderEntity.builder()
                .orderId(1L)
                .customer(customer)
                .courier(courier)
                .restaurant(restaurant)
                .status("PENDING")
                .orderItemList(List.of(orderItem))
                .build();

        // Mock behavior
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        // Call method
        GetOrderDetailResponse response = orderService.getOrderDetail(1L);

        // Verify results
        assertEquals("Budi", response.getCustomerName());
        assertEquals("Andi", response.getCourierName());
        assertEquals("Warung Makan", response.getRestaurantName());
        assertEquals("PENDING", response.getStatus());
        assertNotNull(response.getOrderItemList());
        assertEquals(1, response.getOrderItemList().size());

        GetOrderItemResponse itemResponse = response.getOrderItemList().get(0);
        assertEquals(2, itemResponse.getQuantity());
        assertEquals("Nasi Goreng", itemResponse.getMenuName());
        assertEquals(25000, itemResponse.getMenuPrice());
    }

    @Test
    void testGetOrderDetail_NotFound() {
        // Mock behavior: order not found
        when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Call method and expect exception
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            orderService.getOrderDetail(999L);
        });

        assertEquals("404 NOT_FOUND \"Order ID not found\"", exception.getMessage());
    }

    @Test
    void testUpdateOrderStatus_Success() {
        // Prepare data
        CustomerEntity customer = CustomerEntity.builder().name("Budi").build();
        CourierEntity courier = CourierEntity.builder().name("Andi").build();
        RestaurantEntity restaurant = RestaurantEntity.builder().name("Warung Makan").build();

        OrderEntity order = OrderEntity.builder()
                .orderId(1L)
                .customer(customer)
                .courier(courier)
                .restaurant(restaurant)
                .status("PENDING")
                .build();

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(OrderEntity.class))).thenAnswer(i -> i.getArgument(0));

        UpdateOrderStatusRequest request = UpdateOrderStatusRequest.builder()
                .status("COMPLETED")
                .build();

        // Call method
        UpdateOrderStatusResponse response = orderService.updateOrderStatus(1L, request);

        // Verify
        assertEquals(1L, response.getOrderId());
        assertEquals("Budi", response.getCustomerName());
        assertEquals("Andi", response.getCourierName());
        assertEquals("Warung Makan", response.getRestaurantName());
        assertEquals("COMPLETED", response.getStatus());

        // Verify status updated in order entity
        assertEquals("COMPLETED", order.getStatus());
        assertNotNull(order.getUpdatedAt());

    }

    @Test
    void testUpdateOrderStatus_OrderNotFound() {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());

        UpdateOrderStatusRequest request = UpdateOrderStatusRequest.builder()
                .status("DELIVERED")
                .build();

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> {
            orderService.updateOrderStatus(99L, request);
        });

        assertEquals("404 NOT_FOUND \"Order ID not found\"", ex.getMessage());
    }

    @Test
    void testUpdateOrderStatus_InvalidStatus() {
        CustomerEntity customer = CustomerEntity.builder().name("Budi").build();
        CourierEntity courier = CourierEntity.builder().name("Andi").build();
        RestaurantEntity restaurant = RestaurantEntity.builder().name("Warung Makan").build();

        OrderEntity order = OrderEntity.builder()
                .orderId(1L)
                .customer(customer)
                .courier(courier)
                .restaurant(restaurant)
                .status("PENDING")
                .build();

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        UpdateOrderStatusRequest request = UpdateOrderStatusRequest.builder()
                .status("INVALID_STATUS")
                .build();

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> {
            orderService.updateOrderStatus(1L, request);
        });

        assertEquals("400 BAD_REQUEST \"Invalid status value\"", ex.getMessage());
    }
}
