package com.ecom.OrderService.dto;

import lombok.Builder;

@Builder
public record CancelOrderResponse(
        String message
) {
}
