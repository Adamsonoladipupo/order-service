package com.order_service.order_service.services;

import com.order_service.order_service.data.models.Order;
import com.order_service.order_service.data.models.OrderStatus;
import com.order_service.order_service.data.repositories.OrderRepository;
import com.order_service.order_service.dtos.requests.PlaceOrderRequest;
import com.order_service.order_service.dtos.responses.PlaceOrderResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    private ModelMapper modelMapper;

    public PlaceOrderResponse placeOrder(PlaceOrderRequest request,
                                         UUID buyerId,
                                         String idempotencyKey){
        if (idempotencyKey != null) {
            Optional<Order> existing = orderRepository.findByIdempotencyKey(idempotencyKey);
            if (existing.isPresent()) {
                return modelMapper.map(existing, PlaceOrderResponse.class);
            }
        }

        long totalAmount = request.getItems().stream()
                .mapToLong(item -> item.getUnitPrice() * item.getQty())
                .sum();

        Order order = new Order();
        order.setBuyerId(buyerId);
        order.setIdempotencyKey(idempotencyKey);
        order.setStatus(OrderStatus.RESERVED);
        order.setTotalPrice(totalAmount);
        modelMapper.map(request, order);
        orderRepository.save(order);

        return modelMapper.map(order, PlaceOrderResponse.class);
    }
}
