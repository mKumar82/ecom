package com.ecom.InventoryService.consumer;

import com.ecom.InventoryService.Entity.Inventory;
import com.ecom.InventoryService.Entity.InventoryReservation;
import com.ecom.InventoryService.Entity.ReservationStatus;
import com.ecom.InventoryService.producer.InventoryEventProducer;
import com.ecom.InventoryService.repository.InventoryRepository;
import com.ecom.InventoryService.repository.ReservationRepository;
import com.ecom.InventoryService.service.InventoryService;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class InventoryEventConsumer {

    private final InventoryService inventoryService;
    private final InventoryRepository inventoryRepository;
    private final InventoryEventProducer inventoryEventProducer;
    private final ReservationRepository reservationRepository;

    @Transactional
    @KafkaListener(topics = "order-events",groupId = "inventory-service-group")
    public void handleOrderEvent(JsonNode message) {

        String eventType = message.get("eventType").asText();
        JsonNode payload = message.get("payload");

        UUID orderId = UUID.fromString(payload.get("orderId").asText());

        switch (eventType) {

            case "ORDER_CREATED" -> handleOrderCreated(orderId, payload);

            case "ORDER_CANCELLED" -> handleOrderCancelled(orderId);

            default -> log.warn("⚠️ Unknown eventType {}", eventType);
        }
    }

    @Transactional
    public void handleOrderCreated(UUID orderId, JsonNode payload) {

        JsonNode itemsNode = payload.get("items");

        log.info("📦 Inventory received ORDER_CREATED for orderId={}", orderId);

        if (itemsNode == null || !itemsNode.isArray()) {
            log.error("❌ Invalid ORDER_CREATED payload: items missing");
            inventoryEventProducer.publishInventoryRejected(orderId);
            return;
        }

        for (JsonNode item : itemsNode) {
            UUID productId = UUID.fromString(item.get("productId").asText());
            int quantity = item.get("quantity").asInt();

            boolean reserved = inventoryService.reserveStock(
                    orderId,
                    productId,
                    quantity
            );

            if (!reserved) {
                log.warn("❌ Stock insufficient for productId={}", productId);
                inventoryEventProducer.publishInventoryRejected(orderId);
                return;
            }
        }

        inventoryEventProducer.publishInventoryReserved(orderId);
        log.info("✅ Inventory reserved successfully for orderId={}", orderId);
    }

    @Transactional
    public void handleOrderCancelled(UUID orderId) {

        log.info("📦 Inventory received ORDER_CANCELLED for orderId={}", orderId);

        List<InventoryReservation> reservations =
                reservationRepository.findByOrderId(orderId);

        if (reservations.isEmpty()) {
            log.warn("⚠️ No reservations found for orderId={}", orderId);
            return;
        }

        for (InventoryReservation reservation : reservations) {

            // 🔐 Idempotency guard
            if (reservation.getStatus() != ReservationStatus.RESERVED) {
                log.info("⚠️ Skipping reservation {} with status {}",
                        reservation.getId(), reservation.getStatus());
                continue;
            }

            Inventory inventory = inventoryRepository
                    .findByProductId(reservation.getProductId())
                    .orElseThrow();

            inventory.setAvailableQuantity(
                    inventory.getAvailableQuantity() + reservation.getQuantity()
            );

            inventory.setReserveQuantity(
                    inventory.getReserveQuantity() - reservation.getQuantity()
            );

            reservation.setStatus(ReservationStatus.RELEASED);

            inventoryRepository.save(inventory);
            reservationRepository.save(reservation);
        }

        log.info("🔁 Inventory released successfully for orderId={}", orderId);
    }

    @Transactional
    @KafkaListener(topics = "product-events",groupId = "inventory-service-group")
    public void handleProductCreated(JsonNode message){

        String eventType = message.get("eventType").asText();
        JsonNode payload = message.get("payload");

        if(!"PRODUCT_CREATED".equals(eventType)) return;
        UUID productId = UUID.fromString(payload.get("productId").asText());
        int quantity = payload.get("quantity").asInt();

        Inventory inventory = Inventory.builder()
                .productId(productId)
                .availableQuantity(quantity)
                .reserveQuantity(0)
                .build();

        inventoryRepository.save(inventory);
        log.info("✅ Inventory created for productId={}", productId);
    }

    @Transactional
    @KafkaListener(topics = "payment-events", groupId = "inventory-service-group")
    public void handlePaymentEvents(JsonNode message) {

        String eventType = message.get("eventType").asText();
        UUID orderId = UUID.fromString(message.get("payload").get("orderId").asText());

        log.info("📥 Inventory received {}", eventType);


        if ("PAYMENT_FAILED".equals(eventType)) {

            List<InventoryReservation> reservations =
                    reservationRepository.findByOrderId(orderId);

            for (InventoryReservation reservation : reservations) {

                if (reservation.getStatus() != ReservationStatus.RESERVED) {
                    log.info("⚠️ Ignoring duplicate event {} for order {}", eventType, orderId);
                    return;
                }

                Inventory inventory = inventoryRepository
                        .findByProductId(reservation.getProductId())
                        .orElseThrow();

                inventory.setAvailableQuantity(
                        inventory.getAvailableQuantity() + reservation.getQuantity()
                );
                inventory.setReserveQuantity(
                        inventory.getReserveQuantity() - reservation.getQuantity()
                );

                reservation.setStatus(ReservationStatus.RELEASED);

                inventoryRepository.save(inventory);
                reservationRepository.save(reservation);
            }

            log.info("🔁 Inventory RELEASED for order {}", orderId);
        }

        if ("PAYMENT_COMPLETED".equals(eventType)) {

            List<InventoryReservation> reservations =
                    reservationRepository.findByOrderId(orderId);

            for (InventoryReservation reservation : reservations) {
                reservation.setStatus(ReservationStatus.CONFIRMED);
                reservationRepository.save(reservation);
            }

            log.info("✅ Inventory CONFIRMED for order {}", orderId);
        }
    }


}
