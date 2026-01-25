package com.ecom.InventoryService.dto;

import java.util.UUID;

public record ProductCreatedRequest(
        UUID productId,
        int quantity
) {
}
