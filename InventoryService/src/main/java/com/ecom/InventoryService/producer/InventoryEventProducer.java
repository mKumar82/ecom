package com.ecom.InventoryService.producer;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class InventoryEventProducer {
    private final KafkaTemplate<String,Object> kafkaTemplate;

    private Map<String, Object> baseEvent(String type, String source, Object payload) {
        return Map.of(
                "eventId", UUID.randomUUID().toString(),
                "eventType", type,
                "timestamp", Instant.now().toString(),
                "source", source,
                "payload", payload
        );
    }

    public void publishInventoryReserved(UUID orderId) {

        Map<String, Object> payload = Map.of(
                "orderId", orderId.toString()
//                "productId", productId.toString(),
//                "quantity", quantity,
//                "totalAmount",totalAmount
        );

        kafkaTemplate.send(
                "inventory-events",
                baseEvent("INVENTORY_RESERVED", "INVENTORY_SERVICE", payload)
        );
        log.info("📥 INVENTORY_RESERVED sent → orderId={}",
                orderId);
    }

    public void publishInventoryRejected(UUID orderId) {

        Map<String, Object> payload = Map.of(
                "orderId", orderId.toString()
//                "productId", productId.toString(),
//                "reason", reason
        );

        kafkaTemplate.send(
                "inventory-events",
                baseEvent("INVENTORY_REJECTED", "INVENTORY_SERVICE", payload)
        );
        log.info("📥❌ INVENTORY_REJECTED sent → orderId={}",
                orderId);
    }


}
