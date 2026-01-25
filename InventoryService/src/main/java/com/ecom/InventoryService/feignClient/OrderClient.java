package com.ecom.InventoryService.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;

@FeignClient(name = "ORDERSERVICE",path = "/orders")
public interface OrderClient {

    @PostMapping("/inventory-reserved/{orderId}")
    void inventoryReserved(@PathVariable UUID orderId);

    @PostMapping("/inventory-rejected/{orderId}")
    void inventoryRejected(@PathVariable UUID orderId);
}
