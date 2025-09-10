package com.example.food_delivery_api.dto.customer;

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
public class GetCustomerOrderDetailResponse {

    @JsonProperty("customer_name")
    private String customerName;

    @JsonProperty("order_list")
    private List<GetCustomerOrderResponse> orderList;
}
