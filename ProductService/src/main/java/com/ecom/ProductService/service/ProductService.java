package com.ecom.ProductService.service;

import com.ecom.ProductService.config.KafkaToggleConfig;
import com.ecom.ProductService.dto.CreateProductRequest;
import com.ecom.ProductService.dto.ProductCreatedRequest;
import com.ecom.ProductService.dto.ProductResponse;
import com.ecom.ProductService.entity.Product;
import com.ecom.ProductService.feignClient.InventoryClient;
import com.ecom.ProductService.producer.ProductEventProducer;
import com.ecom.ProductService.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final Optional<ProductEventProducer> productEventProducer;
    private final KafkaToggleConfig kafkaToggleConfig;
    private final InventoryClient inventoryClient;

    public Product createProduct(CreateProductRequest request){
        Product product = Product.builder()
                .title(request.title())
                .price(request.price())
                .availableQuantity(request.availableQuantity())
                .category(request.category())
                .image(request.image())
                .build();

        Product savedProduct = productRepository.save(product);
        if(kafkaToggleConfig.isEnabled()){
            productEventProducer.ifPresent(producer->producer.publishProductCreated(savedProduct));
        }else {
            inventoryClient.createInventory( new ProductCreatedRequest(
                    product.getId(),
                    product.getAvailableQuantity()));
        }
        return savedProduct;
    }

    public Product getById(UUID productId){
        return productRepository.findById(productId)
                .orElseThrow(()->new RuntimeException("product not found"));
    }

    public void updateStock(UUID id, int quantity){
        Product product = getById(id);
        product.setAvailableQuantity(quantity);
        productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<ProductResponse> getProductsByIds(List<UUID> productIds) {

        List<Product> products = productRepository.findAllById(productIds);

        return products.stream()
                .map(this::mapToResponse)
                .toList();
    }

    private ProductResponse mapToResponse(Product product) {
        return ProductResponse.builder()
                .productId(product.getId())
                .name(product.getTitle())
                .price(product.getPrice())
                .imageUrl(product.getImage())
                .build();
    }
}
