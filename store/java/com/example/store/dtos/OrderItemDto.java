package com.example.store.dtos;

import lombok.Data;

@Data
public class OrderItemDto {
    private CartProductDto product;
    private Integer quantity;
    private Integer totalPrice;
}
