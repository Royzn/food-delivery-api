package com.example.food_delivery_api.service;

import com.example.food_delivery_api.dto.menu.CreateMenuRequest;
import com.example.food_delivery_api.dto.menu.CreateMenuResponse;
import com.example.food_delivery_api.dto.restaurant.CreateRestaurantRequest;
import com.example.food_delivery_api.dto.restaurant.CreateRestaurantResponse;
import com.example.food_delivery_api.dto.restaurant.GetRestaurantMenuResponse;
import com.example.food_delivery_api.dto.restaurant.GetRestaurantResponse;

import java.util.List;


public interface RestaurantService {

    CreateRestaurantResponse createRestaurant(CreateRestaurantRequest req);

    List<GetRestaurantResponse> getRestaurantList();

    CreateMenuResponse createRestaurantMenu(Long id, CreateMenuRequest req);

    GetRestaurantMenuResponse getRestaurantMenuList(Long id);
}
