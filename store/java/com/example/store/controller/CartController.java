package com.example.store.controller;

import com.example.store.dtos.AddItemToCartRequest;
import com.example.store.dtos.CartDto;
import com.example.store.dtos.CartItemDto;
import com.example.store.dtos.UpdateCartItemRequest;
import com.example.store.entities.Cart;
import com.example.store.entities.CartItem;
import com.example.store.exceptions.CartNotFoundException;
import com.example.store.exceptions.ProductNotFoundException;
import com.example.store.mappers.CartMapper;
import com.example.store.repositories.CartRepository;
import com.example.store.repositories.ProductRepository;
import com.example.store.services.CartService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/carts")
@AllArgsConstructor
public class CartController {


    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final ProductRepository productRepository;
    private final CartService cartService;

    @PostMapping
    public ResponseEntity<CartDto> createCart(UriComponentsBuilder uriBuilder){
        CartDto cartDto = cartService.createCart();
        URI uri = uriBuilder.path("/carts/{id}").buildAndExpand(cartDto.getId()).toUri();
        return ResponseEntity.created(uri).body(cartDto);
    }

    @PostMapping("/{cartId}/items")
    public ResponseEntity<CartItemDto> addToCart(
         @PathVariable Integer cartId,
         @RequestBody AddItemToCartRequest request
    ){
        //all my comment code belong to service layer
//        Cart cart = cartRepository.findById(cartId).orElse(null);
//        if(cart == null) {
//            return ResponseEntity.notFound().build();
//        }
//
//        //validate product id
//        Product product = productRepository.findById(request.getProductId()).orElse(null);
//        if(product == null){
//            return ResponseEntity.badRequest().build();
//        }
//
//        //if item don't exist in cart, we add it
//        // we increameter the quanlity
//        CartItem cartItem = cart.getItem(product.getId());
//
//        if(cartItem != null){
//            cartItem.setQuantity(cartItem.getQuantity()+1);
//        }
//        else{
//            cartItem = new CartItem();
//            cartItem.setProduct(product);
//            cartItem.setQuantity(1);
//            cartItem.setCart(cart);
//            cart.getItems().add(cartItem);
//        }
//        cartRepository.save(cart);
//
//        CartItemDto cartItemDto = cartMapper.toDto(cartItem);

        CartItemDto cartItemDto = cartService.addToCart(cartId,request.getProductId());
        //this is another way of using status code create, but this do not create a URI....
        return ResponseEntity.status(HttpStatus.CREATED).body(cartItemDto);

    }

    @GetMapping("/{cartId}")
    public ResponseEntity<CartDto> getCart(@PathVariable Integer cartId){
        Cart cart = cartService.getCart(cartId);
        return ResponseEntity.ok(cartMapper.toDto(cart));
    }

    //this method is not refractor using service because i want to keep the comment
    //refer to other methods that has service refractor
    @PutMapping("/{cartId}/items/{productId}")
    public ResponseEntity<?> updateItem(
            @PathVariable("cartId") Integer cartId,
            @PathVariable("productId") Integer productId,
            @Valid @RequestBody UpdateCartItemRequest request
            ){

        Cart cart = cartRepository.findById(cartId).orElse(null);
        if(cart == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("error","cart not found")
            );
        }

        //if item don't exist in cart, we add it
        // we increameter the quanlity
        // by the way we can reuse this logic mutiple places so it is better we extract it into a function
//        CartItem cartItem = cart.getItems().stream()
//                .filter(item -> item.getProduct().getId().equals(productId))
//                .findFirst()
//                .orElse(null);

        CartItem cartItem = cart.getItem(productId);

        if(cartItem == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("error","product not found")
            );
        }

        cartItem.setQuantity(request.getQuantity());
        cartRepository.save(cart);

        return ResponseEntity.ok(cartMapper.toDto(cartItem));
    }

    @DeleteMapping("/{cartId}/items/{productId}")
    public ResponseEntity<?> removeItem(
            @PathVariable("cartId") Integer cartId,
            @PathVariable("productId") Integer productId
    ){
        cartService.deleteCart(cartId,productId);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<Map<String,String>> handleCartNotFound(){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error","Cart not found"));
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Map<String,String>> handleProductNotFound(){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error","Product not found in cart"));
    }
    
}
