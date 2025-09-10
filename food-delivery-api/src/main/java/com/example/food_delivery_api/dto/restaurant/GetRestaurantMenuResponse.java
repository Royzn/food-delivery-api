package com.example.food_delivery_api.dto.restaurant;

import com.example.food_delivery_api.dto.menu.GetMenuResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetRestaurantMenuResponse {
    @JsonProperty("restaurant_name")
    private String restaurantName;

    @JsonProperty("restaurant_menu")
    private List<GetMenuResponse> menuList;
}
