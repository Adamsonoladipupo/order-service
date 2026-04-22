package com.order_service.order_service.services;




import com.order_service.order_service.data.models.Order;
import com.order_service.order_service.data.models.OrderStatus;
import com.order_service.order_service.data.repositories.OrderRepository;
import com.order_service.order_service.dtos.requests.PlaceOrderRequest;
import com.order_service.order_service.dtos.responses.OrderStatusUpdatedResponse;
import com.order_service.order_service.dtos.responses.PlaceOrderResponse;
import com.order_service.order_service.exception.OrderNotFoundException;
import com.order_service.order_service.exception.TimeLapseException;
import com.order_service.order_service.utils.StateValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrderRepository orderRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    public PlaceOrderResponse placeOrder(PlaceOrderRequest request,
                                         UUID buyerId,
                                         String idempotencyKey){
//        if (idempotencyKey != null) {
//            Optional<Order> existing = orderRepository.findByIdempotencyKey(idempotencyKey);
//            if (existing.isPresent()) {
//                return modelMapper.map(existing, PlaceOrderResponse.class);
//            }
//        }
//
//        long totalAmount = request.getItems().stream()
//                .mapToLong(item -> item.getUnitPrice() * item.getQty())
//                .sum();
//
        Order order = new Order();
//        order.setBuyerId(buyerId);
//        order.setIdempotencyKey(idempotencyKey);
//        order.setStatus(OrderStatus.RESERVED);
//        order.setTotalPrice(totalAmount);
//        modelMapper.map(request, order);
//        orderRepository.save(order);

        return modelMapper.map(order, PlaceOrderResponse.class);
    }

    @Override
    public OrderStatusUpdatedResponse updateStatus(String status, String role, UUID id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException("Order not found"));
        if(role==null||!role.equals("ADMIN")){
            if (isUserCancel(status, order)){
                order.setStatus(OrderStatus.CANCELLED);
                return modelMapper.map(orderRepository.save(order), OrderStatusUpdatedResponse.class);
            }

            StateValidator.validate(order.getStatus().toString(),status);
            order.setStatus(OrderStatus.valueOf(status));
            orderRepository.save(order);
            return  modelMapper.map(order, OrderStatusUpdatedResponse.class);
        }
        StateValidator.isAdminOnly(order.getStatus().toString(),status);
        order.setStatus(OrderStatus.valueOf(status));
        orderRepository.save(order);
        return  modelMapper.map(order, OrderStatusUpdatedResponse.class);
    }

    private boolean isUserCancel(String status, Order order) {
        if(status.equals("CANCELLED")&& order.getStatus().toString().equals("RESERVED")){
            if(isGreaterThanOneHour(order)){
                throw new TimeLapseException("order creation is above 1hr, status can not be cancelled");
            }
            StateValidator.validate(order.getStatus().toString(), status);
            return true;
        }
        return false;
    }

    private boolean isGreaterThanOneHour(Order order){
        Duration duration = Duration.between(order.getCreatedAt(),  LocalDateTime.now());
        return duration.toHours() >= 1;
    }
}
