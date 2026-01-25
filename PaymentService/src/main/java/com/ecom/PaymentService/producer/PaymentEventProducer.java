package com.ecom.PaymentService.producer;


import com.ecom.PaymentService.event.PaymentCompletedEvent;
import com.ecom.PaymentService.event.PaymentFailedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
@ConditionalOnProperty(
        name = "app.kafka.enabled",
        havingValue = "true"
)
public class PaymentEventProducer {

    private final Optional<KafkaTemplate<String,Object>> kafkaTemplate;

    private Map<String, Object> baseEvent(String type, String source, Object payload) {
        return Map.of(
                "eventId", UUID.randomUUID().toString(),
                "eventType", type,
                "timestamp", Instant.now().toString(),
                "source", source,
                "payload", payload
        );
    }

    public void publishPaymentCompleted(UUID orderId,UUID paymentId,double totalAmount)
    {
        Map<String, Object> payload = Map.of(
                "orderId", orderId.toString()
//                "paymentId", paymentId.toString(),
//                "totalAmount",totalAmount
        );

        kafkaTemplate.ifPresent(template->template.send(
                "payment-events",
                baseEvent("PAYMENT_COMPLETED", "PAYMENT_SERVICE", payload)
        ));
        log.info("💳 Payment COMPLETED → orderId={}",
                orderId);
    }


    public void publishPaymentFailed(UUID orderId, UUID paymentId, String reason)
    {
        Map<String, Object> payload = Map.of(
                "orderId", orderId.toString()
//                "paymentId", paymentId.toString(),
//                "reason", reason
        );

        kafkaTemplate.ifPresent(template->template.send(
                "payment-events",
                baseEvent("PAYMENT_FAILED", "PAYMENT_SERVICE", payload)
        ));
        log.info("💳❌ Payment FAILED → orderId={}",
                orderId);

    }
}
