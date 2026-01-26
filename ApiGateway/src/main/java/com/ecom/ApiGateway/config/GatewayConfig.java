package com.ecom.ApiGateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("productservice", r -> r.path("/productservice/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://PRODUCTSERVICE"))
                .route("orderservice", r -> r.path("/orderservice/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://ORDERSERVICE"))
                .route("userservice", r -> r.path("/userservice/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://USERSERVICE"))
                .route("paymentservice", r -> r.path("/paymentservice/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://PAYMENTSERVICE"))
                .route("inventoryservice", r -> r.path("/inventoryservice/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://INVENTORYSERVICE"))
                .build();
    }
}