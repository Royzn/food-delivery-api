package com.example.food_delivery_api.service;

import com.example.food_delivery_api.dto.restaurant.CreateRestaurantRequest;
import com.example.food_delivery_api.dto.restaurant.CreateRestaurantResponse;
import com.example.food_delivery_api.dto.restaurant.GetRestaurantResponse;
import com.example.food_delivery_api.entity.RestaurantEntity;
import com.example.food_delivery_api.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService {
    @Autowired
    RestaurantRepository restaurantRepository;

    public ResponseEntity<CreateRestaurantResponse> createRestaurant(CreateRestaurantRequest req){
        RestaurantEntity restaurant = RestaurantEntity.builder()
                .name(req.getRestaurantName())
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
}
