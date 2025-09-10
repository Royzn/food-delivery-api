package com.example.food_delivery_api.dto.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOrderResponse {
    @JsonProperty("order_id")
    private Long orderId;

    @JsonProperty("restaurant_name")
    private String restaurantName;

    @JsonProperty("courier_name")
    private String courierName;

    @JsonProperty("order_status")
    private String status;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;
}
