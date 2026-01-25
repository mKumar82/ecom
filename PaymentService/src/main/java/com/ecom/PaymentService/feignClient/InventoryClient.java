package com.ecom.PaymentService.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@FeignClient(name = "INVENTORYSERVICE",path = "/inventory")
public interface InventoryClient {
    @GetMapping("/payment-completed/{orderId}")
    void paymentCompleted(@PathVariable UUID orderId);

    @GetMapping("/payment-failed/{orderId}")
    void paymentFailed(@PathVariable UUID orderId);
}
