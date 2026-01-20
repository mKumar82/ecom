package com.ecom.ProductService.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record ProductResponse(
        UUID productId,
        String name,
        Double price,
        Integer quantity,
        String imageUrl
) {
}
