package com.example.food_delivery_api.dto.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOrderRequest {
    @NotNull
    @JsonProperty("customer_id")
    private Long customerId;

    @NotNull
    @JsonProperty("restaurant_id")
    private Long restaurantId;

    @NotNull
    @JsonProperty("courier_id")
    private Long courierId;

    @NotEmpty
    @JsonProperty("order_item_list")
    private List<CreateOrderItemRequest> orderItemList;
}
