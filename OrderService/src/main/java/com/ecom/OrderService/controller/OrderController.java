package com.ecom.OrderService.controller;

import com.ecom.OrderService.dto.*;
import com.ecom.OrderService.entity.Order;
import com.ecom.OrderService.entity.OrderStatus;
import com.ecom.OrderService.service.OrderService;
import jakarta.validation.Valid;
import jakarta.ws.rs.Path;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody CreateOrderRequest request,
                                             @RequestHeader("X-User-Id")UUID userId) {
        log.info("++++++++++++++ create order {}",request.orderItems());
        return ResponseEntity.ok(orderService.createOrder(request,userId));
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders(@Valid @RequestHeader("X-User-Id")UUID userId){
        return ResponseEntity.ok(orderService.getAllOrders(userId));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable UUID orderId){
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }

    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<CancelOrderResponse> cancelOrder(@PathVariable UUID orderId){
        return ResponseEntity.ok(orderService.cancelOrder(orderId));
    }

    @PostMapping("/inventory-reserved/{orderId}")
    public ResponseEntity<Void> inventoryReserved(@PathVariable UUID orderId){
        log.info("++++++++++++++ inventory reserved for{}",orderId);
        orderService.setOrderStatus(orderId, OrderStatus.RESERVED);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/inventory-rejected/{orderId}")
    public ResponseEntity<Void> inventoryrejected(@PathVariable UUID orderId){
        log.info("++++++++++++++ inventory rejected {}",orderId);
        orderService.setOrderStatus(orderId, OrderStatus.CANCELLED);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/payment-completed/{orderId}")
    public ResponseEntity<Void> paymentCompleted(@PathVariable UUID orderId){
        log.info("++++++++++++++ payment completed for {}",orderId);
        orderService.setOrderStatus(orderId, OrderStatus.COMPLETED);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/payment-failed/{orderId}")
    public ResponseEntity<Void> paymentFailed(@PathVariable UUID orderId){
        log.info("++++++++++++++ payment failed for {}",orderId);
        orderService.setOrderStatus(orderId, OrderStatus.CANCELLED);
        return ResponseEntity.ok().build();
    }
}
