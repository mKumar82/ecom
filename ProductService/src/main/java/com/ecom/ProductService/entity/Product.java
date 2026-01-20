package com.ecom.ProductService.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Blob;
import java.util.UUID;

@Entity
@Table(name = "products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private double price;
    @Column(nullable = false)
    private int availableQuantity;
    @Column(nullable = false)
    private String image;
    @Column(nullable=false)
    private String category;
}
