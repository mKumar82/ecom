package com.ecom.PaymentService.consumer;


import com.ecom.PaymentService.service.PaymentService;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentEventConsumer {

    private final PaymentService paymentService;

//    @KafkaListener(topics = "inventory-events", groupId = "payment-service-group")
    public void handleInventoryReserved(JsonNode message) {

        String eventType = message.get("eventType").asText();
        JsonNode payload = message.get("payload");

        if (!"INVENTORY_RESERVED".equals(eventType)) return;

        UUID orderId = UUID.fromString(payload.get("orderId").asText());
//        UUID productId = UUID.fromString(payload.get("productId").asText());
//        int quantity = payload.get("quantity").asInt();
        double totalAmount = payload.get("totalAmount").asDouble();

        log.info("📥 INVENTORY_RESERVED received → orderId={}",
                orderId);

        paymentService.createPayment(orderId,totalAmount);

    }


}
