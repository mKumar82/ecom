package com.ecom.OrderService.dto;


import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record CreateOrderRequest(
//        @NotNull
//        UUID userId,
//
//        @NotNull
//        UUID productId,
//
//        @Min(1)
//        int quantity

        @NotEmpty
        List<OrderItemRequest> orderItems
//        double totalAmount

) {
}
