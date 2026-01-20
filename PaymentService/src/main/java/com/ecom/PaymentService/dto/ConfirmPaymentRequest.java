package com.ecom.PaymentService.dto;

import com.ecom.PaymentService.entity.PaymentStatus;

import java.util.UUID;

public record ConfirmPaymentRequest(
        UUID paymentId,
        PaymentStatus status
) {
}
