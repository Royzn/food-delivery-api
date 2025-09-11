package com.example.food_delivery_api;

import com.example.food_delivery_api.dto.courier.CreateCourierRequest;
import com.example.food_delivery_api.dto.courier.CreateCourierResponse;
import com.example.food_delivery_api.entity.CourierEntity;
import com.example.food_delivery_api.repository.CourierRepository;
import com.example.food_delivery_api.service.impl.CourierServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CourierServiceImplTest {

    @Mock
    private CourierRepository courierRepository;

    @InjectMocks
    CourierServiceImpl courierService;

    @BeforeEach
    void setUp(){
        courierRepository = mock(CourierRepository.class);
        courierService = new CourierServiceImpl(courierRepository);
    }

    @Test
    void createCourier_success(){
        LocalDateTime now = LocalDateTime.now();
        CreateCourierRequest request = CreateCourierRequest.builder()
                .name("Andi")
                .build();

        when(courierRepository.save(any(CourierEntity.class)))
                .thenAnswer(invocation -> {
                    CourierEntity input = invocation.getArgument(0);
                    input.setCourierId(10L);         // simulate what DB does
                    input.setCreatedAt(now);
                    input.setUpdatedAt(now);
                    return input;
                });

        CreateCourierResponse response = courierService.createCourier(request);

        assertEquals(10L, response.getId());
        assertEquals("Andi", response.getName());
        assertEquals(now, response.getCreatedAt());
    }

    @Test
    void testCreateCourier_shouldReturnNullId_whenSaveDoesNotSetId() {

        CreateCourierRequest request = CreateCourierRequest.builder()
                .name("Andi")
                .build();

        when(courierRepository.save(any(CourierEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        CreateCourierResponse response = courierService.createCourier(request);

        assertNull(response.getId(), "Expected ID to be null since save() didn't assign one");
        assertEquals("Andi", response.getName());
        assertNotNull(response.getCreatedAt());
    }
}
