package com.example.food_delivery_api.service;

import com.example.food_delivery_api.dto.menu.CreateMenuRequest;
import com.example.food_delivery_api.dto.menu.CreateMenuResponse;
import com.example.food_delivery_api.dto.restaurant.CreateRestaurantRequest;
import com.example.food_delivery_api.dto.restaurant.CreateRestaurantResponse;
import com.example.food_delivery_api.dto.restaurant.GetRestaurantResponse;
import com.example.food_delivery_api.entity.MenuEntity;
import com.example.food_delivery_api.entity.RestaurantEntity;
import com.example.food_delivery_api.repository.MenuRepository;
import com.example.food_delivery_api.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RestaurantService {
    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    MenuRepository menuRepository;

    public ResponseEntity<CreateRestaurantResponse> createRestaurant(CreateRestaurantRequest req){
        RestaurantEntity restaurant = RestaurantEntity.builder()
                .name(req.getRestaurantName())
                .createdAt(LocalDateTime.now())
                .build();

        RestaurantEntity savedRestaurant = restaurantRepository.save(restaurant);

        return ResponseEntity.ok(CreateRestaurantResponse.builder()
                        .restaurantId(savedRestaurant.getRestaurantId())
                        .restaurantName(savedRestaurant.getName())
                .build());
    }

    public ResponseEntity<List<GetRestaurantResponse>> getRestaurantList(){
        List<RestaurantEntity> restaurantEntities = restaurantRepository.findAll();

        List<GetRestaurantResponse> restaurantResponses = restaurantEntities.stream().map(
                restaurantEntity -> GetRestaurantResponse.builder()
                        .restaurantName(restaurantEntity.getName())
                        .build()
        ).toList();

        return ResponseEntity.ok(restaurantResponses);
    }

    public ResponseEntity<CreateMenuResponse> createRestaurantMenu(Long id, CreateMenuRequest req){
        RestaurantEntity r = restaurantRepository.findById(id).orElse(null);

        if(r == null) return ResponseEntity.notFound().build();

        MenuEntity m = MenuEntity.builder()
                .createdAt(LocalDateTime.now())
                .name(req.getMenuName())
                .price(req.getMenuPrice())
                .restaurant(r)
                .build();

        menuRepository.save(m);

        r.setUpdatedAt(LocalDateTime.now());
        restaurantRepository.save(r);

        return ResponseEntity.ok(
                CreateMenuResponse.builder()
                        .restaurantName(r.getName())
                        .menuName(m.getName())
                        .menuPrice(m.getPrice())
                        .build()
        );
    }
}
