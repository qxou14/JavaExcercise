package com.example.store.services;

import com.example.store.dtos.CartDto;
import com.example.store.dtos.CartItemDto;
import com.example.store.entities.Cart;
import com.example.store.entities.CartItem;
import com.example.store.entities.Product;
import com.example.store.exceptions.CartNotFoundException;
import com.example.store.exceptions.ProductNotFoundException;
import com.example.store.mappers.CartMapper;
import com.example.store.repositories.CartRepository;
import com.example.store.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final ProductRepository productRepository;
    
    public CartDto createCart(){
        Cart cart = new Cart();
        cart.setDate_created(LocalDate.now());
        cartRepository.save(cart);
        CartDto cartDto = cartMapper.toDto(cart);
        return cartDto;
    }

    public Cart getCart(Integer cartId){
        //validate cartid
        Cart cart = cartRepository.findById(cartId).orElse(null);
        if(cart == null){
            throw new CartNotFoundException();
        }
        return cart;
    }

    public CartItemDto addToCart(Integer cartId, Integer productId){
        Cart cart = cartRepository.findById(cartId).orElse(null);
        if(cart == null) {
            throw new CartNotFoundException();
        }

        //validate product id
        Product product = productRepository.findById(productId).orElse(null);
        if(product == null){
            throw new ProductNotFoundException();
        }

        //if item don't exist in cart, we add it
        // we increameter the quanlity
        CartItem cartItem = cart.getItem(product.getId());

        if(cartItem != null){
            cartItem.setQuantity(cartItem.getQuantity()+1);
        }
        else{
            cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setQuantity(1);
            cartItem.setCart(cart);
            cart.getItems().add(cartItem);
        }
        cartRepository.save(cart);

        CartItemDto cartItemDto = cartMapper.toDto(cartItem);
        return cartItemDto;
    }

    public void deleteCart(Integer cartId, Integer productId){
        Cart cart = cartRepository.findById(cartId).orElse(null);
        if(cart == null) {
            throw new CartNotFoundException();
        }

        CartItem cartItem = cart.getItem(productId);
        if(cartItem != null){
            cart.getItems().remove(cartItem);
        }

        cartRepository.save(cart);
    }

    public void clearCart(Integer cartId) {
        var cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if (cart == null) {
            throw new CartNotFoundException();
        }
        cart.clear();
        cartRepository.save(cart);
    }
}
