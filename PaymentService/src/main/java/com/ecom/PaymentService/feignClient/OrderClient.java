package com.ecom.InventoryService.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;

@FeignClient(name = "ORDERSERVICE",path = "/orders")
public interface OrderClient {

    @GetMapping("/inventory-reserved/{orderId}")
    void inventoryReserved(UUID orderId);

    @GetMapping("/inventory-rejected/{orderId}")
    void inventoryRejected(UUID orderId);
}
