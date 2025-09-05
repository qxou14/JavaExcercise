package com.example.store.controller;

import com.example.store.config.JwtConfig;
import com.example.store.dtos.JwtResponse;
import com.example.store.dtos.LoginRequest;
import com.example.store.dtos.UserDto;
import com.example.store.mappers.UserMapper;
import com.example.store.repositories.UserRepository;
import com.example.store.services.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserMapper userMapper;
    private final JwtConfig jwtConfig;

    //validate if login creds exist and generate a token
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(
            @Valid @RequestBody LoginRequest request,
            HttpServletResponse response
            ){
        //all of these are replaced by authentication manager
//        var user = userRepository.findByEmail(request.getEmail()).orElse(null);
//        if (user == null){
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }
//
//        //matches function will automatically hash password from request and compare to what we got from db
//        //NOTE: if password is db is not hashed, it will not do the auto hashing, so it will always failed
//        if(!passwordEncoder.matches(request.getPassword(),user.getPassword())){
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword())
        );
        var user = userRepository.findByEmail(request.getEmail()).orElse(null);
        if (user == null){
            return ResponseEntity.notFound().build();
        }
        //access token can be place inside body of response
        var accessToken = jwtService.generateAccessToken(user.getId(), user.getEmail(), user.getName(),user.getRole().toString());
        //refresh token should be placed under http only cookie
        var refreshToken = jwtService.generateRefreshToken(user.getId(), user.getEmail(), user.getName(),user.getRole().toString());

        //name and value
        var cookie = new Cookie("refreshToken",refreshToken);
        //cannot be access by javascript
        cookie.setHttpOnly(true);
        //where cookie is sent to -endpoint that refresh access token
        cookie.setPath("/refresh");
        cookie.setMaxAge(jwtConfig.getRefreshTokenExpiration()); //7days
        cookie.setSecure(true);
        response.addCookie(cookie);

        return ResponseEntity.ok(new JwtResponse(accessToken));
    }

    //validate token
    @PostMapping("/validate")
    public boolean validate(@RequestHeader("Authorization") String authHeader){
        System.out.println("validate called");
        var token = authHeader.replace("Bearer ", "");
        return jwtService.validateToken(token);
    }

    //refresh access token
    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> refresh(
            @CookieValue(value = "refreshToken") String refreshToken
    ){
        if(!jwtService.validateToken(refreshToken)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        var userId = jwtService.getIdFromToken(refreshToken);
        var user = userRepository.findById(userId).orElseThrow();
        var accessToken = jwtService.generateAccessToken(user.getId(),user.getEmail(),user.getName(),user.getRole().toString());

        return ResponseEntity.ok(new JwtResponse(accessToken));
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> me(){
        //this only works becasue we have  SecurityContextHolder.getContext().setAuthentication(authenticaion);
        // in JwtAuthenticationFilter
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //return current user or principal
        Long id = (Long) authentication.getPrincipal();

        //look up the user
        var user = userRepository.findById(id).orElse(null);
        if(user == null){
            return ResponseEntity.notFound().build();
        }
        var userDto = userMapper.toDto(user);
        return ResponseEntity.ok(userDto);

    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Void> handleBadCredentialsException(){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}
