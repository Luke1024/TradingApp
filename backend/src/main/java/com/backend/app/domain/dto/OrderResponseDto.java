package com.backend.app.domain.dto;

public class OrderResponseDto {
    private boolean status;
    private OrderDto orderDto;

    public OrderResponseDto() {
    }

    public OrderResponseDto(boolean status, OrderDto orderDto) {
        this.status = status;
        this.orderDto = orderDto;
    }

    public boolean isStatus() {
        return status;
    }

    public OrderDto getOrderDto() {
        return orderDto;
    }
}
