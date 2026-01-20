package com.ecom.OrderService.consumer;

import com.ecom.OrderService.entity.Order;
import com.ecom.OrderService.entity.OrderStatus;
import com.ecom.OrderService.repository.OrderRepository;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.UUID;


@RequiredArgsConstructor
@Slf4j
@Component
public class OrderEventConsumer {
    private final OrderRepository orderRepository;

    @KafkaListener(topics = "inventory-events",groupId = "order-service-group")
    public void handleInventoryEvents(JsonNode message){

        String eventType = message.get("eventType").asText();
        JsonNode payload = message.get("payload");

        UUID orderId = UUID.fromString(payload.get("orderId").asText());
        Order order = orderRepository.findById(orderId).orElseThrow();
        log.info("📥❌ order consumer lisning inventory → orderId={}",
                eventType);
        if("INVENTORY_REJECTED".equals(eventType)){
            order.setStatus(OrderStatus.CANCELLED);
            orderRepository.save(order);
            log.info("📥❌ INVENTORY_REJECTED received order cancelled → orderId={}",
                    orderId);
        }
        if("INVENTORY_RESERVED".equals(eventType)){
            order.setStatus(OrderStatus.RESERVED);
            orderRepository.save(order);
            log.info("📥 INVENTORY_RESERVED received order reserved→ orderId={}",
                    orderId);
        }
    }

    @KafkaListener(topics = "payment-events",groupId = "order-service-group")
    public void handlePaymentEvents(JsonNode message) {

        String eventType = message.get("eventType").asText();
        JsonNode payload = message.get("payload");

        UUID orderId = UUID.fromString(payload.get("orderId").asText());
        Order order = orderRepository.findById(orderId).orElseThrow();

        if("PAYMENT_COMPLETED".equals(eventType)){
            order.setStatus(OrderStatus.COMPLETED);
            orderRepository.save(order);
            log.info("💳 Payment COMPLETED → orderId={}",
                    orderId);
        }

        if("PAYMENT_FAILED".equals(eventType)){
            order.setStatus(OrderStatus.CANCELLED);
            orderRepository.save(order);
            log.info("💳❌ Payment FAILED → orderId={}",
                    orderId);
        }


    }

}
