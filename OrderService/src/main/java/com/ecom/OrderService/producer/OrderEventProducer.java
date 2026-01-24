package com.ecom.OrderService.producer;

import com.ecom.OrderService.entity.Order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
@ConditionalOnProperty(
        name = "app.kafka.enabled",
        havingValue = "true"
)
public class OrderEventProducer {

    private final Optional<KafkaTemplate<String, Object>> kafkaTemplate;

    public void publishOrderCreated(Order order){

        Map<String, Object> payload = Map.of(
                "orderId", order.getId(),
                "items", order.getOrderItems().stream()
                        .map(item -> Map.of(
                                "productId", item.getProductId(),
                                "quantity", item.getQuantity()
                        ))
                        .toList()
        );

        Map<String, Object> event = Map.of(
                "eventId", UUID.randomUUID().toString(),
                "eventType", "ORDER_CREATED",
                "source", "ORDER_SERVICE",
                "timestamp", Instant.now().toString(),
                "payload", payload
        );

        kafkaTemplate.ifPresent(template->template.send("order-events", event));
        log.info("📤 ORDER_CREATED event sent {}", order.getId());

    }


    public void publishOrderCancel(Order order){

        Map<String, Object> payload = Map.of(
                "orderId", order.getId()
//                "items", order.getOrderItems().stream()
//                        .map(item -> Map.of(
//                                "productId", item.getProductId(),
//                                "quantity", item.getQuantity()
//                        ))
//                        .toList()
        );

        Map<String, Object> event = Map.of(
                "eventId", UUID.randomUUID().toString(),
                "eventType", "ORDER_CANCEL",
                "source", "ORDER_SERVICE",
                "timestamp", Instant.now().toString(),
                "payload", payload
        );

        kafkaTemplate.ifPresent(template->template.send("order-events", event));
        log.info("📤 ORDER_CANCEL event sent {}", order.getId());

    }

}
