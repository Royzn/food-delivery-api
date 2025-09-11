package com.example.food_delivery_api;

import com.example.food_delivery_api.dto.menu.CreateMenuRequest;
import com.example.food_delivery_api.dto.menu.CreateMenuResponse;
import com.example.food_delivery_api.dto.restaurant.CreateRestaurantRequest;
import com.example.food_delivery_api.dto.restaurant.CreateRestaurantResponse;
import com.example.food_delivery_api.dto.restaurant.GetRestaurantMenuResponse;
import com.example.food_delivery_api.dto.restaurant.GetRestaurantResponse;
import com.example.food_delivery_api.entity.MenuEntity;
import com.example.food_delivery_api.entity.RestaurantEntity;
import com.example.food_delivery_api.repository.MenuRepository;
import com.example.food_delivery_api.repository.RestaurantRepository;
import com.example.food_delivery_api.service.impl.RestaurantServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RestaurantServiceImplTest {
    @Mock
    private RestaurantRepository restaurantRepository;
    @Mock
    private MenuRepository menuRepository;

    @InjectMocks
    RestaurantServiceImpl restaurantService;

    @BeforeEach
    void setUp(){
        restaurantRepository = mock(RestaurantRepository.class);
        menuRepository = mock(MenuRepository.class);
        restaurantService = new RestaurantServiceImpl(restaurantRepository, menuRepository);
    }

    @Test
    void testCreateRestaurant() {
        CreateRestaurantRequest req = CreateRestaurantRequest.builder()
                .restaurantName("Kopi Mantap")
                .build();

        // Mock save to simulate DB assigning ID
        when(restaurantRepository.save(any(RestaurantEntity.class))).thenAnswer(invocation -> {
            RestaurantEntity arg = invocation.getArgument(0);
            return RestaurantEntity.builder()
                    .restaurantId(100L)  // simulated generated ID
                    .name(arg.getName())
                    .status(arg.getStatus())
                    .createdAt(arg.getCreatedAt())
                    .build();
        });

        CreateRestaurantResponse response = restaurantService.createRestaurant(req);

        assertEquals(100L, response.getRestaurantId());
        assertEquals("Kopi Mantap", response.getRestaurantName());
    }

    @Test
    void testGetRestaurantList() {
        // Prepare mock data
        RestaurantEntity r1 = RestaurantEntity.builder()
                .name("Resto A")
                .status("OPEN")
                .build();

        RestaurantEntity r2 = RestaurantEntity.builder()
                .name("Resto B")
                .status("CLOSED")
                .build();

        when(restaurantRepository.findAll()).thenReturn(List.of(r1, r2));

        // Call method
        List<GetRestaurantResponse> responseList = restaurantService.getRestaurantList();

        // Assertions
        assertEquals(2, responseList.size());

        assertEquals("Resto A", responseList.get(0).getRestaurantName());
        assertEquals("OPEN", responseList.get(0).getStatus());

        assertEquals("Resto B", responseList.get(1).getRestaurantName());
        assertEquals("CLOSED", responseList.get(1).getStatus());
    }

    @Test
    void testCreateRestaurantMenu_Success() {
        // Arrange
        Long restaurantId = 1L;

        CreateMenuRequest request = CreateMenuRequest.builder()
                .menuName("Ayam Geprek")
                .menuPrice(20000D)
                .build();

        RestaurantEntity mockRestaurant = RestaurantEntity.builder()
                .restaurantId(restaurantId)
                .name("Resto Mantap")
                .build();

        // Mock findById
        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(mockRestaurant));

        // Mock menuRepository.save (simulate save behavior)
        when(menuRepository.save(any(MenuEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Mock restaurantRepository.save for updating timestamp
        when(restaurantRepository.save(any(RestaurantEntity.class))).thenReturn(mockRestaurant);

        // Act
        CreateMenuResponse response = restaurantService.createRestaurantMenu(restaurantId, request);

        // Assert
        assertEquals("Resto Mantap", response.getRestaurantName());
        assertEquals("Ayam Geprek", response.getMenuName());
        assertEquals(20000, response.getMenuPrice());
    }

    @Test
    void testCreateRestaurantMenu_RestaurantNotFound() {
        Long invalidId = 999L;

        CreateMenuRequest request = CreateMenuRequest.builder()
                .menuName("Nasi Uduk")
                .menuPrice(15000D)
                .build();

        when(restaurantRepository.findById(invalidId)).thenReturn(Optional.empty());

        CreateMenuResponse response = restaurantService.createRestaurantMenu(invalidId, request);

        assertNull(response);
    }

    @Test
    void testGetRestaurantMenuList_Success() {
        Long restaurantId = 1L;

        // Sample menu entities
        MenuEntity menu1 = MenuEntity.builder()
                .name("Ayam Goreng")
                .price(25000D)
                .build();

        MenuEntity menu2 = MenuEntity.builder()
                .name("Nasi Uduk")
                .price(15000D)
                .build();

        // Sample restaurant with menu list
        RestaurantEntity restaurant = RestaurantEntity.builder()
                .restaurantId(restaurantId)
                .name("Warung Bunda")
                .menuList(List.of(menu1, menu2))
                .build();

        // Mocking repository behavior
        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(restaurant));

        // Call method
        GetRestaurantMenuResponse response = restaurantService.getRestaurantMenuList(restaurantId);

        assertEquals("Warung Bunda", response.getRestaurantName());
        assertEquals(2, response.getMenuList().size());

        assertEquals("Ayam Goreng", response.getMenuList().get(0).getMenuName());
        assertEquals(25000, response.getMenuList().get(0).getMenuPrice());

        assertEquals("Nasi Uduk", response.getMenuList().get(1).getMenuName());
        assertEquals(15000, response.getMenuList().get(1).getMenuPrice());
    }

    @Test
    void testGetRestaurantMenuList_RestaurantNotFound() {
        Long invalidId = 999L;

        when(restaurantRepository.findById(invalidId)).thenReturn(Optional.empty());

        GetRestaurantMenuResponse response = restaurantService.getRestaurantMenuList(invalidId);

        assertNull(response);
    }
}
