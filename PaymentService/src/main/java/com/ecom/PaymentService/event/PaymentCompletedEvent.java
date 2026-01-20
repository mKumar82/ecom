package com.ecom.PaymentService.event;

import java.util.UUID;

public record PaymentCompletedEvent(
        UUID orderId,
        UUID paymentId
) {
}
