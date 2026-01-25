package com.ecom.ProductService.dto;

import java.util.UUID;

public record ProductCreatedRequest(
        UUID productId,
        int quantity
) {
}
