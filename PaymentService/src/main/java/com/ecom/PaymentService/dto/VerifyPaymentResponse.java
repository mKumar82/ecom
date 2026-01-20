package com.ecom.PaymentService.dto;

import lombok.Builder;

@Builder
public record VerifyPaymentResponse(
        String status
) {
}
