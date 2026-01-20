package com.ecom.OrderService.dto;

import java.util.UUID;

public record ProductResponse(
        UUID productId,
        String name,
        Double price,
        Integer quantity,
        String imageUrl
) {
}
