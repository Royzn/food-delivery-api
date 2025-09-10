package com.example.food_delivery_api.dto.menu;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateMenuResponse {
    @JsonProperty("restaurant_name")
    private String restaurantName;

    @JsonProperty("menu_name")
    private String menuName;

    @JsonProperty("menu_price")
    private Double menuPrice;
}
