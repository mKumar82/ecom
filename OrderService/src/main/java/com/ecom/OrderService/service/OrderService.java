package com.ecom.OrderService.service;

import com.ecom.OrderService.dto.*;
import com.ecom.OrderService.entity.Order;
import com.ecom.OrderService.entity.OrderItem;
import com.ecom.OrderService.entity.OrderStatus;
import com.ecom.OrderService.feignClient.ProductClient;
import com.ecom.OrderService.feignClient.UserClient;
import com.ecom.OrderService.producer.OrderEventProducer;
import com.ecom.OrderService.repository.OrderRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderEventProducer orderEventProducer;
    private final ProductClient productClient;
    private final UserClient userClient;

    @Transactional
    public OrderResponse createOrder(CreateOrderRequest request, UUID userId){

        List<ProductResponse> products = productClient.getProductsByIds(
                request.orderItems().stream()
                        .map(OrderItemRequest::productId)
                        .toList()
        );

        log.info("products received : {}",products);

        Order order = Order.builder()
                .userId(userId)
                .status(OrderStatus.CREATED)
                .createdAt(LocalDateTime.now())
                .build();

        // 3️⃣ Create Order Items + calculate total
        List<OrderItem> orderItems = new ArrayList<>();
        double totalAmount = 0;

        for (OrderItemRequest itemReq : request.orderItems()) {
            ProductResponse product = products.stream()
                    .filter(p -> p.productId().equals(itemReq.productId()))
                    .findFirst()
                    .orElseThrow();

            double itemTotal = product.price() * itemReq.quantity();
            totalAmount += itemTotal;

            orderItems.add(
                    OrderItem.builder()
                            .order(order)
                            .productId(product.productId())
                            .name(product.name())
                            .price(product.price())
                            .quantity(itemReq.quantity())
                            .imageUrl(product.imageUrl())
                            .build()
            );
        }

        order.setOrderItems(orderItems);
        order.setTotalAmount(totalAmount);

        Order savedOrder = orderRepository.save(order);
        orderEventProducer.publishOrderCreated(savedOrder);

        OrderResponse orderResponse = OrderResponse.builder()
                .id(savedOrder.getId())
                .userId(savedOrder.getUserId())
                .totalAmount(savedOrder.getTotalAmount())
                .status(savedOrder.getStatus().name())
                .createdAt(savedOrder.getCreatedAt())
                .items(
                        savedOrder.getOrderItems().stream()
                                .map(item -> OrderItemResponse.builder()
                                        .productId(item.getProductId())
                                        .name(item.getName())
                                        .price(item.getPrice())
                                        .quantity(item.getQuantity())
                                        .imageUrl(item.getImageUrl())
                                        .build())
                                .toList()
                )
                .build();


        return orderResponse;
    }

    public List<OrderResponse> getAllOrders(@Valid UUID userId) {
        List<Order> orders = orderRepository.findByUserId(userId);

        return orders.stream()
                .map(order -> OrderResponse.builder()
                        .id(order.getId())
                        .userId(order.getUserId())
                        .totalAmount(order.getTotalAmount())
                        .status(order.getStatus().name())
                        .createdAt(order.getCreatedAt())
                        .items(
                                order.getOrderItems().stream()
                                        .map(item -> OrderItemResponse.builder()
                                                .productId(item.getProductId())
                                                .name(item.getName())
                                                .price(item.getPrice())
                                                .quantity(item.getQuantity())
                                                .imageUrl(item.getImageUrl())
                                                .build()
                                        )
                                        .toList()
                        )
                        .build()
                )
                .toList();

    }

    public OrderResponse getOrderById(UUID orderId){
        Order savedOrder =  orderRepository.findById(orderId).orElseThrow();

        OrderResponse orderResponse = OrderResponse.builder()
                .id(savedOrder.getId())
                .userId(savedOrder.getUserId())
                .totalAmount(savedOrder.getTotalAmount())
                .status(savedOrder.getStatus().name())
                .createdAt(savedOrder.getCreatedAt())
                .items(
                        savedOrder.getOrderItems().stream()
                                .map(item -> OrderItemResponse.builder()
                                        .productId(item.getProductId())
                                        .name(item.getName())
                                        .price(item.getPrice())
                                        .quantity(item.getQuantity())
                                        .imageUrl(item.getImageUrl())
                                        .build())
                                .toList()
                )
                .build();


        return orderResponse;
    }

    public CancelOrderResponse cancelOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        orderRepository.delete(order);
        orderEventProducer.publishOrderCancel(order);
        return CancelOrderResponse.builder().message("order deleted successfully").build();
    }
}
