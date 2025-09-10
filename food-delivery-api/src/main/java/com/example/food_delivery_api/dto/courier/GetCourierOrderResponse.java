package com.example.food_delivery_api.dto.courier;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetCourierOrderResponse {

    @JsonProperty("customer_name")
    private String customerName;

    @JsonProperty("restaurant_name")
    private String restaurantName;

    @JsonProperty("order_status")
    private String orderStatus;

    @JsonProperty("order_item")
    List<GetCourierOrderItemResponse> orderItemList;
}
