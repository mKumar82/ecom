package com.ecom.InventoryService.controller;

import com.ecom.InventoryService.dto.ProductCreatedRequest;
import com.ecom.InventoryService.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
@Slf4j
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping("/product-created")
    public ResponseEntity<Void> createInventory(
            @RequestBody ProductCreatedRequest request
    ) {
        log.info("++++++++++++++++++++++using inventory controller++++++++++++++++++++++++++++");
        inventoryService.createInventory(
                request
        );
        return ResponseEntity.ok().build();
    }
}
