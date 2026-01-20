package com.ecom.PaymentService.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record PaymentResponse(
        UUID paymentId,
        String redirectUrl
) {
}
