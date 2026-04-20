package com.order_service.order_service.services;

import com.order_service.order_service.data.repositories.OrderRepository;
import com.order_service.order_service.dtos.requests.PlaceOrderRequest;
import com.order_service.order_service.dtos.responses.PlaceOrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public PlaceOrderResponse placeOrder(PlaceOrderRequest request,
                                         UUID buyerId,
                                         String idempotencyKey){
        
        return null;
    }
}
