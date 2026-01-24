package com.ecom.OrderService.feignClient;

import com.ecom.OrderService.dto.OrderCreatedRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@FeignClient(name = "INVENTORYSERVICE",path = "/inventory")
public interface InventoryClient {
    @PostMapping("/order-created")
    void orderCreated(@RequestBody OrderCreatedRequest request);

    @GetMapping("/order-cancelled/{orderId}")
    void orderCancelled(@PathVariable UUID orderId);
}
