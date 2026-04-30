package com.order_service.order_service.dtos.responses;

import lombok.Data;

@Data
public class ApiResponse<T> {
    private boolean success;
    private T data;

    public ApiResponse(T data) {
        this.success = data!=null;
        this.data = data;
    }
}
