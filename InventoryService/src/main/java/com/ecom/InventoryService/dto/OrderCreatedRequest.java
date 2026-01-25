package com.ecom.InventoryService.dto;

import java.util.List;
import java.util.UUID;

public record OrderCreatedRequest(
        UUID orderId,
        List<OrderItemRequest> orderItems
) {
}
