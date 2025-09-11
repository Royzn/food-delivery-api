package com.example.food_delivery_api.controller;

import com.example.food_delivery_api.dto.menu.CreateMenuRequest;
import com.example.food_delivery_api.dto.menu.CreateMenuResponse;
import com.example.food_delivery_api.dto.menu.GetMenuResponse;
import com.example.food_delivery_api.dto.order.GetOrderDetailResponse;
import com.example.food_delivery_api.dto.restaurant.CreateRestaurantRequest;
import com.example.food_delivery_api.dto.restaurant.CreateRestaurantResponse;
import com.example.food_delivery_api.dto.restaurant.GetRestaurantMenuResponse;
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
        CreateRestaurantResponse res = restaurantService.createRestaurant(req);
        if(res == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(res);
    }

    //get restaurant list
    @GetMapping
    public ResponseEntity<List<GetRestaurantResponse>> getRestaurantList(){
        List<GetRestaurantResponse> res = restaurantService.getRestaurantList();
        if(res == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(res);
    }

    @PostMapping(value = "/{id}/menus", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreateMenuResponse> createRestaurantMenu(@PathVariable Long id, @Valid @RequestBody CreateMenuRequest req){
        CreateMenuResponse res = restaurantService.createRestaurantMenu(id, req);
        if(res == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(res);
    }

    @GetMapping("/{id}/menus")
    public ResponseEntity<GetRestaurantMenuResponse> getRestaurantMenuList(@PathVariable Long id){
        GetRestaurantMenuResponse res = restaurantService.getRestaurantMenuList(id);
        if(res == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(res);
    }
}
