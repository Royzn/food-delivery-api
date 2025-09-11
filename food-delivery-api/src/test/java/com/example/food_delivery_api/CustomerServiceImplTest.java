package com.example.food_delivery_api;

import com.example.food_delivery_api.dto.customer.*;
import com.example.food_delivery_api.entity.*;
import com.example.food_delivery_api.repository.CustomerRepository;
import com.example.food_delivery_api.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CustomerServiceImplTest {
    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    CustomerServiceImpl customerService;

    @BeforeEach
    void setUp(){
        customerRepository = mock(CustomerRepository.class);
        customerService = new CustomerServiceImpl(customerRepository);
    }

    @Test
    void testCreateCustomer_Success() {
        // Prepare request
        CreateCustomerRequest request = CreateCustomerRequest.builder()
                .name("Budi")
                .build();

        LocalDateTime now = LocalDateTime.now();

        // Mock repository save method
        when(customerRepository.save(any(CustomerEntity.class))).thenAnswer(invocation -> {
            CustomerEntity cust = invocation.getArgument(0);
            cust.setCustomerId(1L);
            cust.setName("Budi");
            cust.setCreatedAt(now);
            cust.setUpdatedAt(now);
            return cust;
        });

        // Call method
        CreateCustomerResponse response = customerService.createCustomer(request);

        // Verify
        assertEquals(1L, response.getId());
        assertEquals("Budi", response.getName());
        assertEquals(now, response.getCreatedAt());
    }

    @Test
    void testGetCustomerOrderDetail_Success() {
        // Prepare entities and nested data

        // Menu entity
        MenuEntity menu = MenuEntity.builder()
                .name("Nasi Goreng")
                .price(25000D)
                .build();

        // Order item
        OrderItemEntity orderItem = OrderItemEntity.builder()
                .quantity(2L)
                .menu(menu)
                .build();

        // Courier entity
        CourierEntity courier = CourierEntity.builder()
                .name("Andi")
                .build();

        // Restaurant entity
        RestaurantEntity restaurant = RestaurantEntity.builder()
                .name("Warung Makan")
                .build();

        // Order entity with items
        OrderEntity order = OrderEntity.builder()
                .courier(courier)
                .restaurant(restaurant)
                .status("PENDING")
                .orderItemList(List.of(orderItem))
                .build();

        // Customer entity with orders
        CustomerEntity customer = CustomerEntity.builder()
                .name("Budi")
                .orderList(List.of(order))
                .build();

        // Mock repository
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        // Call method
        GetCustomerOrderDetailResponse response = customerService.getCustomerOrderDetail(1L);

        // Assertions
        assertEquals("Budi", response.getCustomerName());

        assertNotNull(response.getOrderList());
        assertEquals(1, response.getOrderList().size());

        GetCustomerOrderResponse orderResponse = response.getOrderList().get(0);
        assertEquals("Andi", orderResponse.getCourierName());
        assertEquals("Warung Makan", orderResponse.getRestaurantName());
        assertEquals("PENDING", orderResponse.getOrderStatus());

        assertNotNull(orderResponse.getOrderItemList());
        assertEquals(1, orderResponse.getOrderItemList().size());

        GetCustomerOrderItemResponse itemResponse = orderResponse.getOrderItemList().get(0);
        assertEquals(2, itemResponse.getQuantity());
        assertEquals("Nasi Goreng", itemResponse.getMenuName());
        assertEquals(25000, itemResponse.getMenuPrice());
    }

    @Test
    void testGetCustomerOrderDetail_CustomerNotFound() {
        when(customerRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> {
            customerService.getCustomerOrderDetail(999L);
        });

        assertEquals("404 NOT_FOUND \"Customer not found\"", ex.getMessage());
    }
}
