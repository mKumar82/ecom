package com.ecom.OrderService.controller;

import com.ecom.OrderService.dto.CancelOrderResponse;
import com.ecom.OrderService.dto.CreateOrderRequest;
import com.ecom.OrderService.dto.OrderResponse;
import com.ecom.OrderService.entity.Order;
import com.ecom.OrderService.service.OrderService;
import jakarta.validation.Valid;
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
        log.info("++++++++++++++ {}",request.orderItems());
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
}
