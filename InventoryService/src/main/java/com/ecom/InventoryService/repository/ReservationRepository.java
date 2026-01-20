package com.ecom.InventoryService.repository;

import com.ecom.InventoryService.Entity.Inventory;
import com.ecom.InventoryService.Entity.InventoryReservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReservationRepository extends JpaRepository<InventoryReservation, UUID> {
    List<InventoryReservation> findByOrderId(UUID orderId);
}
