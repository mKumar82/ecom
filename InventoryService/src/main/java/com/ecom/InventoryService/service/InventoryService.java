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

    public void createInventory(ProductCreatedRequest request){
        Inventory inventory = Inventory.builder()
                .productId(request.productId())
                .availableQuantity(request.quantity())
                .reserveQuantity(0)
                .build();

        inventoryRepository.save(inventory);
    }
}
