package com.example.store.repositories;

import com.example.store.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    //might return a user or might return empty  instead of null
    Optional<User> findByEmail(String email);
}
