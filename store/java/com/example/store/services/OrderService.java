package com.example.store.services;

import com.example.store.dtos.OrderDto;
import com.example.store.entities.Address;
import com.example.store.exceptions.OrderNotFoundException;
import com.example.store.mappers.OrderMapper;
import com.example.store.repositories.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {

//    private PaymentService paymentService;
//    public OrderService(@Qualifier("paypal") PaymentService paymentService){
//        this.paymentService = paymentService;
//    }
//    public void placeOrder(){
//        paymentService.processPayment(123);
//    }

    private final AuthService authService;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public List<OrderDto> getAllOrders(){
        var user = authService.getCurrentUser();
        var orders = orderRepository.getAllByCustomer(user);
        return orders.stream().map(order -> orderMapper.toDto(order)).toList();
    }


    public OrderDto getOrder(Integer orderId) {
//        var order = orderRepository.getOrderWithItems(orderId).orElse(null);
//        if(order ==null){
//            throw new OrderNotFoundException();
//        }

        var order = orderRepository
                .getOrderWithItems(orderId)
                .orElseThrow(() -> new OrderNotFoundException());

        return orderMapper.toDto(order);

    }
}
