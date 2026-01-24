package com.ecom.ProductService.feignClient;

import com.ecom.ProductService.dto.ProductCreatedRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "INVENTORYSERVICE",path = "/inventory")
public interface InventoryClient {
    @PostMapping("/product-created")
    void createInventory(@RequestBody ProductCreatedRequest request);
}
