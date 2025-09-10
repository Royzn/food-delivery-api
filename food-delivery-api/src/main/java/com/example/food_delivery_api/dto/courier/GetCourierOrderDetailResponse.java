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
public class GetCourierOrderDetailResponse {

    @JsonProperty("courier_name")
    private String courierName;

    @JsonProperty("order_list")
    private List<GetCourierOrderResponse> orderList;
}
