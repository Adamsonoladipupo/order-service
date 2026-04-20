package com.order_service.order_service.data.repositories;

import com.order_service.order_service.data.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findByBuyerId(UUID buyerId);
    List<Order> findBySellerId(UUID sellerId);
    Optional<Order> findByIdempotencyKey(String key);
    Optional<Order> findById(UUID id);

}
