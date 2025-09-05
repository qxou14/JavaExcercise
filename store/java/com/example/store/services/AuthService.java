package com.example.store.services;

import com.example.store.entities.User;
import com.example.store.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    public User getCurrentUser(){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //return current user or principal
//        Long id = (Long) authentication.getPrincipal();
        Long id = 12L;

        //look up the user
        return userRepository.findById(id).orElse(null);
    }
}
