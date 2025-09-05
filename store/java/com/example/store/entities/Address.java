package com.example.store.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "addresses")
@Entity
public class Address {

    @Id
    //auto increment
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="street")
    private String street;

    @Column(name="city")
    private String city;

    @Column(name="zip")
    private String zip;


    @Column(name="state")
    private String state;


    //forign keys
    //many adddress can have one use
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
