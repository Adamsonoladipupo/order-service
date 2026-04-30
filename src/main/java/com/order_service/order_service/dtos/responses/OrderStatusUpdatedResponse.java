package com.order_service.order_service.dtos.responses;

import com.order_service.order_service.data.models.OrderItem;
import com.order_service.order_service.data.models.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
@Data
public class OrderStatusUpdatedResponse {
    private UUID id;
    private UUID buyerId;
    private UUID sellerId;
    private OrderStatus status;
    private Long totalPrice;
    private String currency;
    private String paymentMethod;
    private String shippingAddress;
    private List<OrderItem> items;
    private String idempotencyKey;
    private LocalDateTime cancelledAt;
    private String cancelReason;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
