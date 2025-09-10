package com.example.food_delivery_api.dto.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrderStatusResponse {

    @JsonProperty("order_id")
    private Long orderId;

    @JsonProperty("customer_name")
    private String customerName;

    @JsonProperty("courier_name")
    private String courierName;

    @JsonProperty("restaurant_name")
    private String restaurantName;

    @JsonProperty("status")
    private String status;
}
