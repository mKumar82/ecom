package com.ecom.InventoryService.controller;

import com.ecom.InventoryService.dto.OrderCreatedRequest;
import com.ecom.InventoryService.dto.OrderItemRequest;
import com.ecom.InventoryService.dto.ProductCreatedRequest;
import com.ecom.InventoryService.feignClient.OrderClient;
import com.ecom.InventoryService.service.InventoryService;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
@Slf4j
public class InventoryController {

    private final InventoryService inventoryService;
    private final OrderClient orderClient;

    @PostMapping("/product-created")
    public ResponseEntity<Void> createInventory(@RequestBody ProductCreatedRequest request) {
        log.info("++++++++++++++++++++++using inventory controller++++++++++++++++++++++++++++");
        inventoryService.createInventory(
                request
        );
        return ResponseEntity.ok().build();
    }

    @PostMapping("/order-created")
    public ResponseEntity<Void> orderCreated(@RequestBody OrderCreatedRequest request){
        log.info("++++++++++++++ order created received in inventory {}",request.orderItems());
        UUID orderId = request.orderId();
        for (OrderItemRequest item : request.orderItems()) {
            UUID productId = UUID.fromString(item.productId().toString());
            int quantity = item.quantity();

            boolean reserved = inventoryService.reserveStock(
                    orderId,
                    productId,
                    quantity
            );
            if (!reserved) {
                log.warn("❌ Stock insufficient for productId={}", productId);
                orderClient.inventoryRejected(orderId);
            }
        }
        orderClient.inventoryReserved(orderId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/order-cancelled/{orderId}")
    public ResponseEntity<Void> orderCancelled(@PathVariable UUID orderId){
        log.info("++++++++++++++ order cancel received in inventory {}",orderId);
        inventoryService.releaseStock(orderId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/payment-completed/{orderId}")
    public ResponseEntity<Void> paymentCompleted(@PathVariable UUID orderId){
        log.info("++++++++++++++ payment completed received in inventory {}",orderId);
        inventoryService.confirmStock(orderId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/payment-failed/{orderId}")
    public ResponseEntity<Void> paymentFailed(@PathVariable UUID orderId){
        log.info("++++++++++++++ payment failed received in inventory {}",orderId);
        inventoryService.releaseStock(orderId);
        return ResponseEntity.ok().build();
    }
}
