package com.ecom.OrderService.repository;

import com.ecom.OrderService.entity.Order;
import jakarta.validation.Valid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findByUserId(@Valid UUID userId);
}