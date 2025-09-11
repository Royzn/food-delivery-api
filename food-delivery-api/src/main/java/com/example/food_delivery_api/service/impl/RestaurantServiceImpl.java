package com.example.food_delivery_api.service.impl;

import com.example.food_delivery_api.dto.menu.CreateMenuRequest;
import com.example.food_delivery_api.dto.menu.CreateMenuResponse;
import com.example.food_delivery_api.dto.menu.GetMenuResponse;
import com.example.food_delivery_api.dto.restaurant.CreateRestaurantRequest;
import com.example.food_delivery_api.dto.restaurant.CreateRestaurantResponse;
import com.example.food_delivery_api.dto.restaurant.GetRestaurantMenuResponse;
import com.example.food_delivery_api.dto.restaurant.GetRestaurantResponse;
import com.example.food_delivery_api.entity.MenuEntity;
import com.example.food_delivery_api.entity.RestaurantEntity;
import com.example.food_delivery_api.entity.RestaurantStatusEnum;
import com.example.food_delivery_api.repository.MenuRepository;
import com.example.food_delivery_api.repository.RestaurantRepository;
import com.example.food_delivery_api.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;

    @Override
    public CreateRestaurantResponse createRestaurant(CreateRestaurantRequest req){
        RestaurantEntity restaurant = RestaurantEntity.builder()
                .name(req.getRestaurantName())
                .status(RestaurantStatusEnum.OPEN.toString())
                .createdAt(LocalDateTime.now())
                .build();

        RestaurantEntity savedRestaurant = restaurantRepository.save(restaurant);

        return CreateRestaurantResponse.builder()
                .restaurantId(savedRestaurant.getRestaurantId())
                .restaurantName(savedRestaurant.getName())
                .build();
    }

    @Override
    public List<GetRestaurantResponse> getRestaurantList(){
        List<RestaurantEntity> restaurantEntities = restaurantRepository.findAll();

        return restaurantEntities.stream().map(
                restaurantEntity -> GetRestaurantResponse.builder()
                        .restaurantName(restaurantEntity.getName())
                        .status(restaurantEntity.getStatus())
                        .build()
        ).toList();
    }

    @Override
    public CreateMenuResponse createRestaurantMenu(Long id, CreateMenuRequest req){
        RestaurantEntity r = restaurantRepository.findById(id).orElse(null);

        if(r == null) return null;

        MenuEntity m = MenuEntity.builder()
                .createdAt(LocalDateTime.now())
                .name(req.getMenuName())
                .price(req.getMenuPrice())
                .restaurant(r)
                .build();

        menuRepository.save(m);

        r.setUpdatedAt(LocalDateTime.now());
        restaurantRepository.save(r);

        return CreateMenuResponse.builder()
                        .restaurantName(r.getName())
                        .menuName(m.getName())
                        .menuPrice(m.getPrice())
                        .build();
    }

    @Override
    public GetRestaurantMenuResponse getRestaurantMenuList(Long id){
        RestaurantEntity r = restaurantRepository.findById(id).orElse(null);

        if(r == null) return null;

        List<MenuEntity> menuList = r.getMenuList();

        List<GetMenuResponse> menuResponses = menuList.stream().map(
                menu -> GetMenuResponse.builder()
                        .menuName(menu.getName())
                        .menuPrice(menu.getPrice())
                        .build()
        ).toList();

        return GetRestaurantMenuResponse.builder()
                        .restaurantName(r.getName())
                        .menuList(menuResponses)
                        .build();
    }
}
