package com.order_service.order_service.services;

import com.order_service.order_service.data.models.Order;
import com.order_service.order_service.data.models.OrderStatus;
import com.order_service.order_service.data.repositories.OrderRepository;
import com.order_service.order_service.dtos.responses.OrderStatusUpdatedResponse;
import com.order_service.order_service.exception.IllegalStateTransition;
import com.order_service.order_service.exception.TimeLapseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class OrderServiceImplTest {
    @Autowired
    private OrderRepository  orderRepository;
    @Autowired
            private OrderService orderService;
    Order order;

    @BeforeEach
    void setUp() {
         order = new Order();
        order.setStatus(OrderStatus.RESERVED);
        orderRepository.save(order);
    }

    @Test
    void testThatOrderServiceStateCanBeUpdatedWithPaymentCompletedEvent() {
        OrderStatusUpdatedResponse orderStatusUpdatedResponse = orderService.updateStatus("CONFIRMED",null,order.getId());
        assertEquals(OrderStatus.CONFIRMED,orderStatusUpdatedResponse.getStatus());
    }

    @Test
    void testThatOrderServiceStateCanBUpdatedToFailed(){
        OrderStatusUpdatedResponse orderStatusUpdatedResponse = orderService.updateStatus("FAILED",null,order.getId());
        assertEquals(OrderStatus.FAILED,orderStatusUpdatedResponse.getStatus());
    }

    @Test
    void testThatOrderServiceCanBeUpdatedToCancelled(){
        order.setStatus(OrderStatus.PENDING);
        orderRepository.save(order);
        OrderStatusUpdatedResponse orderStatusUpdatedResponse = orderService.updateStatus("CANCELLED",null,order.getId());
        assertEquals(OrderStatus.CANCELLED,orderStatusUpdatedResponse.getStatus());
    }

    @Test
    void testThatOrderServiceReturnsCancelledIfUserCancelWithinOneHour(){
        OrderStatusUpdatedResponse orderStatusUpdatedResponse = orderService.updateStatus("CANCELLED",null,order.getId());
        assertEquals(OrderStatus.CANCELLED,orderStatusUpdatedResponse.getStatus());
    }

    @Test
    void testThatOrderServiceReturnsErrorIfUserCancelAboveOnehour(){
        order.setCreatedAt(LocalDateTime.now().minusHours(1).minusMinutes(10));
        orderRepository.save(order);
        assertThrows(TimeLapseException.class,()-> orderService.updateStatus("CANCELLED",null,order.getId()));
    }

    @Test
    void testPendingToReserved() {
        order.setStatus(OrderStatus.PENDING);
        orderRepository.save(order);

        OrderStatusUpdatedResponse response =
                orderService.updateStatus("RESERVED", null, order.getId());

        assertEquals(OrderStatus.RESERVED, response.getStatus());
    }

    @Test
    void testReservedToConfirmed() {
        order.setStatus(OrderStatus.RESERVED);
        orderRepository.save(order);

        OrderStatusUpdatedResponse response =
                orderService.updateStatus("CONFIRMED", null, order.getId());

        assertEquals(OrderStatus.CONFIRMED, response.getStatus());
    }


    @Test
    void testReservedToFailed() {
        order.setStatus(OrderStatus.RESERVED);
        orderRepository.save(order);

        OrderStatusUpdatedResponse response =
                orderService.updateStatus("FAILED", null, order.getId());

        assertEquals(OrderStatus.FAILED, response.getStatus());
    }


    @Test
    void testInvalidTransitionShouldThrowException() {
        order.setStatus(OrderStatus.PENDING);
        orderRepository.save(order);

        assertThrows(IllegalStateTransition.class, () -> {
            orderService.updateStatus("CONFIRMED", null, order.getId());
        });
    }

    @Test
    void testConfirmedToCancelledWithoutAdminShouldFail() {
        order.setStatus(OrderStatus.CONFIRMED);
        orderRepository.save(order);

        assertThrows(IllegalStateTransition.class, () -> {
            orderService.updateStatus("CANCELLED", null, order.getId());
        });
    }

}