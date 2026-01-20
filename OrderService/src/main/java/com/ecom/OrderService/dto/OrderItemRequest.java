package com.ecom.OrderService.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record OrderItemRequest(
        @NotNull
        UUID productId,
//         String name,
//         double price,
        @Min(1)
        Integer quantity
//         String imageUrl
) {
}
