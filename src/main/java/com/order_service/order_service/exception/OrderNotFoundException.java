package com.order_service.order_service.exception;

public class OrderNotFoundException extends OrderServiceException {
    public OrderNotFoundException(String orderNotFound) {
        super(orderNotFound);
    }
}
