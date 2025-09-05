package com.example.store.dtos;

import lombok.Data;

@Data
public class CheckoutResponse {

    private Integer orderId;

    public CheckoutResponse(Integer orderId) {
        this.orderId = orderId;
    }
}
