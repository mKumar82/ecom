package com.ecom.OrderService.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
public record OrderResponse(
        UUID id,
        UUID userId,
        Double totalAmount,
        String status,
        LocalDateTime createdAt,
        List<OrderItemResponse> items
) {
}
