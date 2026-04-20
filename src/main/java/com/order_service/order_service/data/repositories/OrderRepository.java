package com.order_service.order_service.data.repositories;

import com.order_service.order_service.data.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {

}
