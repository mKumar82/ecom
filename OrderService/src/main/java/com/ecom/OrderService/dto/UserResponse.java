package com.ecom.OrderService.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserResponse(
        UUID id,
        String name,
        String email,
        String status,
        LocalDateTime createdAt
) {
}
