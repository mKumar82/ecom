package com.ecom.PaymentService.dto;

import java.util.UUID;

public record PaymentRequest(
        UUID orderId,
        double totalAmount
) {
}
