package com.example.store.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name ="users")
@Setter
@Getter
@ToString
public class User {

    //marks primary key
    @Id
    //auto increment
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name="id")
    private Integer id;

    @Column(name="role")
    //jpa will store enum as String to db
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false, name="name")
    private String name;

    @Column(nullable = false, name="email")
    private String email;

    @Column(nullable = false, name="password")
    private String password;


    //one user can have mutiple addresses
    //this like the ownership, user belong to address which is a owner.
    // adddress has a forign key from user ! thats what determines ownership
    @OneToMany(mappedBy = "user")
    private List<Address> addresses = new ArrayList<>();

//    public void addAddress(Address address){
//        addresses.add(address);
//        address.setUser(this);
//    }
//
//    public void removeAddress(Address address){
//        addresses.remove(address);
//        address.setUser(null);
//    }

    //for many to many , you GET TO choose the owning table, usually the table that gets upadte frequently
    //cascade goes to the owning table
    //many to many relationship
    @ManyToMany
    //join table for many to many, join column for many to one
    @JoinTable(name = "user_tags", joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "tag_id"))
    //joinColumn : owning side pk  , inverseJoinColumn: other side
    private Set<Tag> tags = new HashSet<>();

//    public void addTag(String tagName){
//        var tag = new Tag(tagName);
//        tags.add(tag);
//        tag.getUsers().add(this);
//    }

    //one to one relationship
    @OneToOne(mappedBy = "user")
    private Profile profile;


}
