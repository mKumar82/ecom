package com.ecom.OrderService.consumer;

import com.ecom.OrderService.entity.Order;
import com.ecom.OrderService.entity.OrderStatus;
import com.ecom.OrderService.repository.OrderRepository;

import com.ecom.OrderService.service.OrderService;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.UUID;


@RequiredArgsConstructor
@Slf4j
@Component
@ConditionalOnProperty(
        name = "app.kafka.enabled",
        havingValue = "true"
)
public class OrderEventConsumer {
    private final OrderService orderService;

    @KafkaListener(topics = "inventory-events",groupId = "order-service-group")
    public void handleInventoryEvents(JsonNode message){

        String eventType = message.get("eventType").asText();
        JsonNode payload = message.get("payload");

        UUID orderId = UUID.fromString(payload.get("orderId").asText());
        log.info("📥❌ order consumer lisning inventory → orderId={}",
                eventType);
        if("INVENTORY_REJECTED".equals(eventType)){
            orderService.setOrderStatus(orderId,OrderStatus.CANCELLED);
            log.info("📥❌ INVENTORY_REJECTED received order cancelled → orderId={}",
                    orderId);
        }
        if("INVENTORY_RESERVED".equals(eventType)){
            orderService.setOrderStatus(orderId,OrderStatus.RESERVED);
            log.info("📥 INVENTORY_RESERVED received order reserved→ orderId={}",
                    orderId);
        }
    }

    @KafkaListener(topics = "payment-events",groupId = "order-service-group")
    public void handlePaymentEvents(JsonNode message) {

        String eventType = message.get("eventType").asText();
        JsonNode payload = message.get("payload");

        UUID orderId = UUID.fromString(payload.get("orderId").asText());

        if("PAYMENT_COMPLETED".equals(eventType)){
            orderService.setOrderStatus(orderId,OrderStatus.COMPLETED);
            log.info("💳 Payment COMPLETED → orderId={}",
                    orderId);
        }

        if("PAYMENT_FAILED".equals(eventType)){
            orderService.setOrderStatus(orderId,OrderStatus.CANCELLED);
            log.info("💳❌ Payment FAILED → orderId={}",
                    orderId);
        }


    }

}
