package com.ecom.ProductService.producer;

import com.ecom.ProductService.entity.Product;
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
@RequiredArgsConstructor
@Slf4j
@ConditionalOnProperty(
        name = "app.kafka.enabled",
        havingValue = "true"
)
public class ProductEventProducer {
    private final Optional<KafkaTemplate<String,Object>> kafkaTemplate;

    public void publishProductCreated(Product product){

        Map<String,Object> payload = Map.of(
                "productId",product.getId(),
                "quantity",product.getAvailableQuantity()
        );

        Map<String,Object> event = Map.of(
                "eventId", UUID.randomUUID().toString(),
                "eventType", "PRODUCT_CREATED",
                "source", "PRODUCT_SERVICE",
                "timestamp", Instant.now().toString(),
                "payload", payload
        );

//        kafkaTemplate.send("product-events",event);
        kafkaTemplate.ifPresent(template->template.send("product-events",event));
        log.info("📤 PRODUCT_CREATED event sent {}",product.getId());
    }
}
