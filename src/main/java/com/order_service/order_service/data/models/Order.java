package com.order_service.order_service.data.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private UUID buyerId;
    private UUID sellerId;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private Long totalPrice;
    private String currency;
    private String paymentMethod;
    private String shippingAddress;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "id")
    private List<OrderItem> items;
    private String idempotencyKey;
    private LocalDateTime cancelledAt;
    private String cancelReason;
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

   public Order() {
       this.createdAt = LocalDateTime.now();
   }

}
