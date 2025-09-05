package com.example.store.services.impl;

import com.example.store.services.PaymentService;
import org.springframework.stereotype.Service;
//this can be used as label for Qualifer
@Service("stripe")
public class StripePaymentService implements PaymentService {
    @Override
    public void processPayment(double amount){
        System.out.println("stripe amount: "+ amount);
    }
}
