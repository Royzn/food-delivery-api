package com.example.food_delivery_api.controller;

import com.example.food_delivery_api.dto.menu.CreateMenuRequest;
import com.example.food_delivery_api.dto.menu.CreateMenuResponse;
import com.example.food_delivery_api.dto.restaurant.CreateRestaurantRequest;
import com.example.food_delivery_api.dto.restaurant.CreateRestaurantResponse;
import com.example.food_delivery_api.dto.restaurant.GetRestaurantResponse;
import com.example.food_delivery_api.service.RestaurantService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {
    @Autowired
    RestaurantService restaurantService;

    //add restaurant
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreateRestaurantResponse> createRestaurant(@Valid @RequestBody CreateRestaurantRequest req){
        return restaurantService.createRestaurant(req);
    }

    //get restaurant list
    @GetMapping
    public ResponseEntity<List<GetRestaurantResponse>> getRestaurantList(){
        return restaurantService.getRestaurantList();
    }

    @PostMapping(value = "/{id}/menus", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreateMenuResponse> createRestaurantMenu(@PathVariable Long id, @Valid @RequestBody CreateMenuRequest req){
        return restaurantService.createRestaurantMenu(id, req);
    }
}
