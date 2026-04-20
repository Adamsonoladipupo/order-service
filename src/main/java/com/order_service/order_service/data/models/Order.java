package com.order_service.order_service.data.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private UUID id;
    private UUID buyerId;
    private OrderStatus status;
    private Long totalPrice;
    private String currency;
    private String paymentMethod;
    private ShippingAddress shippingAddress;
    private List<OrderItem> items;
    private String idempotencyKey;
    private LocalDateTime cancelledAt;
    private String cancelReason;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
