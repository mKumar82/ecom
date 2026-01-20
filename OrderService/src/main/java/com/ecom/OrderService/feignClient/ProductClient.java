package com.ecom.OrderService.feignClient;

import com.ecom.OrderService.dto.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "PRODUCTSERVICE",url = "http://localhost:8085")
public interface ProductClient {

    @GetMapping("/products/{id}")
    public ProductResponse getProduct(@PathVariable UUID id);

    @PostMapping("/products/by-ids")
    List<ProductResponse> getProductsByIds(
            @RequestBody List<UUID> productIds
    );
}
