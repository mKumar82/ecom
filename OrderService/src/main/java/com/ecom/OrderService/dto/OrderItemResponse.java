package com.ecom.OrderService.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record OrderItemResponse(
        UUID productId,
        String name,
        Double price,
        Integer quantity,
        String imageUrl
) {
}
