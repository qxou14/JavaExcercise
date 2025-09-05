package com.example.store.controller;

import com.example.store.dtos.ChangePasswordRequest;
import com.example.store.dtos.RegisterUserRequest;
import com.example.store.dtos.UpdateUserRequest;
import com.example.store.dtos.UserDto;
import com.example.store.entities.Role;
import com.example.store.entities.User;
import com.example.store.mappers.UserMapper;
import com.example.store.repositories.UserRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {


    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    // without DTO
//    @RequestMapping(method = RequestMethod.GET, value = "/user")
//    public ResponseEntity<List<User>> getAllUsers(){
//        List<User> userList = userRepository.findAll();
//        return ResponseEntity.ok(userList);
//    }
    //adding DTO But without mapper
//    @RequestMapping(method = RequestMethod.GET, value = "/user")
//    public ResponseEntity<List<UserDto>> getAllUsers(){
//        List<UserDto> userList = userRepository.findAll()
//                .stream()
//                .map(user -> new UserDto(user.getId(),user.getName(),user.getEmail()))
//                .toList();
//        return ResponseEntity.ok(userList);
//    }

    //adding DTO and add mapper
    @RequestMapping(method = RequestMethod.GET, value = "/user")
    public ResponseEntity<List<UserDto>> getAllUsers(
            @RequestHeader(name = "x-auth-token") String token,
            @RequestParam(required = false, defaultValue = "name") String sort){
        System.out.println(token);
        List<UserDto> userList = userRepository.findAll(Sort.by(sort))
                .stream()
                .map(user -> userMapper.toDto(user))
                .toList();
        return ResponseEntity.ok(userList);
    }


    //dto without mapper
//    @RequestMapping(method = RequestMethod.GET, value = "/user/{id}")
//    public ResponseEntity<UserDto> getUser(@PathVariable Long id){
//        User user = userRepository.findById(id).orElse(null);
//        if(user == null) {
//            return ResponseEntity.notFound().build();
//        }
//
//        UserDto userDto = new UserDto(user.getId(), user.getName(), user.getEmail());
//        return ResponseEntity.ok(userDto);
//
//    }

    //dto with mapper
    @RequestMapping(method = RequestMethod.GET, value = "/user/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id){
        User user = userRepository.findById(id).orElse(null);
        if(user == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(userMapper.toDto(user));

    }

    @RequestMapping(method = RequestMethod.POST, value = "/user")
    //if validation fails, this method won't get call and will return  MethodArgumentNotValidException
    public ResponseEntity<UserDto> createUser(
            @Valid @RequestBody RegisterUserRequest request,
            UriComponentsBuilder uriBuilder){

        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        userRepository.save(user);

        UserDto userDto = userMapper.toDto(user);
        //this create a header called location under postman
        // {id} is from user.getId()
        URI uri = uriBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri();

        //status 201
        return ResponseEntity.created(uri).body(userDto);
    }



    ///

    @RequestMapping(method = RequestMethod.PUT, value = "/user/{id}")
    public ResponseEntity<UserDto> updateUser(
            @PathVariable(name = "id") Long id,
            @RequestBody UpdateUserRequest request){

        User user = userRepository.findById(id).orElse(null);

        if(user == null){
            return ResponseEntity.notFound().build();
        }
        //map request to user
        userMapper.update(request,user);
        userRepository.save(user);

        return ResponseEntity.ok(userMapper.toDto(user));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable(name = "id") Long id) {
        User user = userRepository.findById(id).orElse(null);

        if(user == null){
            return ResponseEntity.notFound().build();
        }
        userRepository.delete(user);
        //status 204
        return ResponseEntity.noContent().build();
    }

    //changing user password or submit apporval request
    //this represents an action so we use post to update request.
    @PostMapping("/user/{id}/change-password")
    public ResponseEntity<Void> changePassword(
            @PathVariable Long id,
            @RequestBody ChangePasswordRequest request
    ){
        User user = userRepository.findById(id).orElse(null);

        if(user == null){
            return ResponseEntity.notFound().build();
        }

        if(!user.getPassword().equals(request.getOldPassword())){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        user.setPassword(request.getNewPassword());
        userRepository.save(user);
        return ResponseEntity.noContent().build();

    }


}
