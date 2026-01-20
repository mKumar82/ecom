package com.ecom.ProductService.dto;

public record CreateProductRequest(
        String title,
        double price,
        int availableQuantity,
        String category,
        String image
) {
}
