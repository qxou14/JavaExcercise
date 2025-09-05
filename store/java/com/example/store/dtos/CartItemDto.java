package com.example.store.dtos;

import lombok.Data;

@Data
public class CartItemDto {
    private OrderProductDto product;
    private Integer quantity;
    private Integer totalPrice;

}
