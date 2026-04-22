package com.order_service.order_service.services;

import com.order_service.order_service.dtos.requests.PlaceOrderRequest;
import com.order_service.order_service.dtos.responses.OrderStatusUpdatedResponse;
import com.order_service.order_service.dtos.responses.PlaceOrderResponse;

import java.util.UUID;

public interface OrderService {
    PlaceOrderResponse placeOrder(PlaceOrderRequest request,
                                  UUID buyerId,
                                  String idempotencyKey);


    OrderStatusUpdatedResponse updateStatus(String status, String role, UUID id);
}
