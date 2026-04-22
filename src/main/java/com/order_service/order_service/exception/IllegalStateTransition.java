package com.order_service.order_service.exception;

public class IllegalStateTransition extends OrderServiceException {
    public IllegalStateTransition(String s) {
        super(s);
    }
}
