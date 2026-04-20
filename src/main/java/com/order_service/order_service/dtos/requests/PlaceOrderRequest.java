package com.order_service.order_service.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlaceOrderRequest {
    private List<OrderItemRequest> items;
    private ShippingAddressDto shippingAddress;
    private String paymentMethod;
}
