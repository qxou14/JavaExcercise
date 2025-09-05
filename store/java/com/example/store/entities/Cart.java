package com.example.store.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name="carts")
public class Cart {
    @Column(name = "id")
    @Id
    //auto increment
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="date_created")
    private LocalDate date_created;

    //create a field called cart
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    //cascade goes here (unlike one to one, that belong to owning side)
    //this is NOT owning side
    private Set<CartItem> items = new HashSet<>();

    public Integer getTotalPrice(){
        return items.stream()
                .map(cartItem -> cartItem.getTotalPrice())
                .reduce(0,Integer::sum);
    }

    //we assign this function here due to a principle
    // Information Expert Principle: assign the responsibility to the class that has the
    // necessary data to do the job
    public CartItem getItem(Integer productId){
        CartItem cartItem = items.stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);
        return cartItem;
    }

    public void clear() {
        items.clear();
    }



}
