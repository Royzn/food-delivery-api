package com.example.food_delivery_api.dto.restaurant;

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
public class GetRestaurantResponse {
    @JsonProperty("restaurant_name")
    private String restaurantName;

    @JsonProperty("status")
    private String status;
}
