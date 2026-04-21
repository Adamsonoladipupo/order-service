package com.order_service.order_service.services;

import com.order_service.order_service.data.models.Order;
import com.order_service.order_service.data.models.OrderStatus;
import com.order_service.order_service.data.repositories.OrderRepository;
import com.order_service.order_service.dtos.requests.PlaceOrderRequest;
import com.order_service.order_service.dtos.responses.PlaceOrderResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrderRepository orderRepository;
    private ModelMapper modelMapper;

    @Override
    public PlaceOrderResponse placeOrder(PlaceOrderRequest request, UUID buyerId){

        long totalAmount = request.getItems().stream()
                .mapToLong(item -> item.getUnitPrice() * item.getQty())
                .sum();

        Order order = new Order();
        order.setBuyerId(buyerId);
        order.setStatus(OrderStatus.RESERVED);
        order.setTotalPrice(totalAmount);
        modelMapper.map(request, order);
        orderRepository.save(order);

        return modelMapper.map(order, PlaceOrderResponse.class);
    }

    @Override
    public Order cancelOrder(UUID orderId, UUID userId) {
        return null;
    }

    @Override
    public void handlePaymentCompleted(UUID orderId) {

    }

    @Override
    public void handlePaymentFailed(UUID orderId) {

    }

    @Override
    public Order getOrder(UUID orderId, UUID userId) {
        return null;
    }

    @Override
    public List<Order> listOrders(UUID userId) {
        return List.of();
    }
}
