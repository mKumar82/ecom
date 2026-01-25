package com.ecom.InventoryService.service;

import com.ecom.InventoryService.Entity.Inventory;
import com.ecom.InventoryService.Entity.InventoryReservation;
import com.ecom.InventoryService.Entity.ReservationStatus;
import com.ecom.InventoryService.dto.ProductCreatedRequest;
import com.ecom.InventoryService.repository.InventoryRepository;
import com.ecom.InventoryService.repository.ReservationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;
    private final ReservationRepository reservationRepository;

    @Transactional
    public boolean reserveStock(UUID orderId, UUID productId, int quantity) {

        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new RuntimeException("Product Not Found"));

        if (inventory.getAvailableQuantity() < quantity) {
            throw new RuntimeException("Insufficient stock");
        }

        inventory.setAvailableQuantity(inventory.getAvailableQuantity() - quantity);
        inventory.setReserveQuantity(inventory.getReserveQuantity() + quantity);

        InventoryReservation reservation = InventoryReservation.builder()
                        .orderId(orderId)
                                .productId(productId)
                                        .quantity(quantity)
                                                .status(ReservationStatus.RESERVED)
                                                        .build();

        inventoryRepository.save(inventory);
        reservationRepository.save(reservation);

        return true;
    }

    @Transactional
    public boolean releaseStock(UUID orderId){
        List<InventoryReservation> reservations =
                reservationRepository.findByOrderId(orderId);

        if (reservations.isEmpty()) {
            log.warn("⚠️ No reservations found for orderId={}", orderId);
            throw new RuntimeException("No reservations found");
        }

        for (InventoryReservation reservation : reservations) {

            // 🔐 Idempotency guard
            if (reservation.getStatus() != ReservationStatus.RESERVED) {
                log.info("⚠️ Skipping reservation {} with status {}",
                        reservation.getId(), reservation.getStatus());
                continue;
            }

            Inventory inventory = inventoryRepository
                    .findByProductId(reservation.getProductId())
                    .orElseThrow();

            inventory.setAvailableQuantity(
                    inventory.getAvailableQuantity() + reservation.getQuantity()
            );

            inventory.setReserveQuantity(
                    inventory.getReserveQuantity() - reservation.getQuantity()
            );

            reservation.setStatus(ReservationStatus.RELEASED);

            inventoryRepository.save(inventory);
            reservationRepository.save(reservation);
        }
        return true;
    }

    @Transactional
    public void createInventory(ProductCreatedRequest request){
        Inventory inventory = Inventory.builder()
                .productId(request.productId())
                .availableQuantity(request.quantity())
                .reserveQuantity(0)
                .build();

        inventoryRepository.save(inventory);
    }

    @Transactional
    public void confirmStock(UUID orderId){
        List<InventoryReservation> reservations =
                reservationRepository.findByOrderId(orderId);

        for (InventoryReservation reservation : reservations) {
            reservation.setStatus(ReservationStatus.CONFIRMED);
            reservationRepository.save(reservation);
        }
    }
}
