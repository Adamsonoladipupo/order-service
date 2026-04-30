package com.order_service.order_service.services;

import com.order_service.order_service.data.models.Order;
import com.order_service.order_service.data.models.User;
import com.order_service.order_service.dtos.requests.PlaceOrderRequest;
import com.order_service.order_service.dtos.responses.OrderStatusUpdatedResponse;
import com.order_service.order_service.dtos.responses.PlaceOrderResponse;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    PlaceOrderResponse placeOrder(PlaceOrderRequest request, UUID buyerId);
    Order cancelOrder(UUID orderId, UUID userId, User role);
    void handlePaymentCompleted(UUID orderId);
    void handlePaymentFailed(UUID orderId);
    Order getOrder(UUID orderId, UUID userId);
    List<Order> listOrders(UUID userId);
}
