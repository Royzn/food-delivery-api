package com.example.food_delivery_api.dto.courier;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetCourierOrderItemResponse {

    @JsonProperty("quantity")
    private Long quantity;

    @JsonProperty("menu_name")
    private String menuName;

    @JsonProperty("menu_price")
    private Double menuPrice;
}
