package com.ecom.OrderService.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.time.LocalDateTime;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name="orders")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Order {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private UUID userId;

//    @Column(nullable = false)
//    private UUID productId;

//    @Column(nullable = false)
//    @Min(1)
//    private int quantity;

    @Column(nullable = false)
    private double totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @Column(nullable = false)
    private LocalDateTime createdAt;


    private LocalDateTime updatedAt;

    @OneToMany(
            mappedBy = "order",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<OrderItem> orderItems;





//    public void markPaymentCompleted(){
//        this.status = OrderStatus.PAID;
//    }

//    public void markPaymentFailed(){
//        this.status = OrderStatus.CANCELLED;
//    }

}
