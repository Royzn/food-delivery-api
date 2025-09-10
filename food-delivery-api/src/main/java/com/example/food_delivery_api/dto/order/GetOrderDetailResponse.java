package com.example.food_delivery_api.dto.order;

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
public class GetOrderDetailResponse {

    @JsonProperty("customer_name")
    private String customerName;

    @JsonProperty("courier_name")
    private String courierName;

    @JsonProperty("restaurant_name")
    private String restaurantName;

    @JsonProperty("order_list")
    private List<GetOrderItemResponse> orderItemList;
}
