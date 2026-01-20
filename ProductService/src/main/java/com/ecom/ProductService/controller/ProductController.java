package com.ecom.ProductService.controller;

import com.ecom.ProductService.dto.CreateProductRequest;
import com.ecom.ProductService.dto.ProductResponse;
import com.ecom.ProductService.entity.Product;
import com.ecom.ProductService.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
@Slf4j
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public Product create(@RequestBody CreateProductRequest request){
        return productService.createProduct(request);
    }

    @GetMapping
    public List<Product> getAllProducts(){
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product getById(@PathVariable UUID id){
        return productService.getById(id);
    }

    @PutMapping("/{id}/stock")
    public void update(@PathVariable UUID id,@RequestParam int quantity){
        productService.updateStock(id,quantity);
    }

    @PostMapping("/by-ids")
    public List<ProductResponse> getProductsByIds(
            @RequestBody List<UUID> productIds
    ) {
        return productService.getProductsByIds(productIds);
    }
}
