package com.example.store.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="cart_items")
public class CartItem {
    @Column(name = "id")
    @Id
    //auto increment
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(nullable = false, name="quantity")
    private Integer quantity;

    public Integer getTotalPrice(){
        return product.getPrice() * quantity;
    }

}
