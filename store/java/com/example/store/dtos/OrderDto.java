package com.example.store.dtos;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDto {
    private Integer Id;
    private String status;
    private LocalDateTime createdAt;
    private List<OrderItemDto> items;
    private Integer totalPrice;
}
