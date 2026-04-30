package com.order_service.order_service.controller;

import com.order_service.order_service.dtos.responses.ApiResponse;
import com.order_service.order_service.services.OrderService;
import com.order_service.order_service.services.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderHandler {

    @Autowired
    private OrderService orderService;

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<?>> updateOrderStatus(String status, @RequestHeader("X-User-Role") String role, @PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(orderService.updateStatus(status,role,id)));
    }


}
