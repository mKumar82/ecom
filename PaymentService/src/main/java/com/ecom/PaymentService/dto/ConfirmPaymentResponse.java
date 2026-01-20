package com.ecom.PaymentService.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record ConfirmPaymentResponse(
        String message
) {
}
