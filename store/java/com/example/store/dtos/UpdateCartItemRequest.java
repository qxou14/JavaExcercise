package com.example.store.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateCartItemRequest {
    @NotNull(message = "quantity must be provided")
    private Integer quantity;
//    difference between int quantity - int is not null, so if we want to explicity handle null message, we use Integer
}
