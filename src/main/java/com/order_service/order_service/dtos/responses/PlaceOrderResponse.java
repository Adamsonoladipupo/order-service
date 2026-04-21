package com.order_service.order_service.dtos.responses;

import com.order_service.order_service.dtos.requests.ShippingAddressDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlaceOrderResponse {
    private UUID orderId;
    private String status;
    private long totalAmount;
    private String currency;
    private String paymentMethod;
    private List<OrderItemResponse> items;
    private ShippingAddressDto shippingAddress;
    private LocalDateTime createdAt;
}
