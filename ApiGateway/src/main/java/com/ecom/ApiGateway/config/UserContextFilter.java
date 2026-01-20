package com.ecom.ApiGateway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

//@Component
//public class UserContextFilter implements GlobalFilter {
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//
//        return exchange.getPrincipal()
//                .cast(JwtAuthenticationToken.class)
//                .flatMap(auth -> {
//
//                    String userId = auth.getToken().getClaim("userId");
//
//                    ServerHttpRequest mutatedRequest =
//                            exchange.getRequest()
//                                    .mutate()
//                                    .header("X-User-Id", userId)
//                                    .build();
//
//                    return chain.filter(exchange.mutate()
//                            .request(mutatedRequest)
//                            .build());
//                });
//    }
//}
@Component
@Slf4j
public class UserContextFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        return exchange.getPrincipal()
                .doOnNext(p -> log.info("🔐 Principal class = {}", p.getClass()))
                .cast(JwtAuthenticationToken.class)
                .flatMap(auth -> {

//                    String userId = auth.getToken().getClaimAsString("userId");
                    String userId = auth.getToken().getSubject();
                    log.info("👤 Extracted userId from JWT = {}", userId);


                    ServerHttpRequest mutatedRequest = exchange.getRequest()
                            .mutate()
                            .header("X-User-Id", userId)
                            .build();

                    return chain.filter(exchange.mutate()
                            .request(mutatedRequest)
                            .build());
                })
                .switchIfEmpty(chain.filter(exchange)); // VERY IMPORTANT
    }
}