package com.example.food_delivery_api.dto.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOrderItemRequest {
    @NotNull
    @JsonProperty("menu_id")
    private Long menuId;

    @NotNull
    @JsonProperty("quantity")
    private Long quantity;
}
