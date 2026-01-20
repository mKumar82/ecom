package com.ecom.PaymentService.event;

import java.util.UUID;

public record PaymentFailedEvent(
        UUID orderId,
        String reason
) {
}
