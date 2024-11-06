package com.dev.ritividhi.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.dev.ritividhi.models.Cart;
import com.dev.ritividhi.models.User;
import com.dev.ritividhi.repository.CartRepository;

@Service
public class CartService {
	
    private final CartRepository cartRepository;
    private final UserService userService;
    
    public CartService(CartRepository cartRepository, UserService userService) {
        this.cartRepository = cartRepository;
        this.userService = userService;
    }
       
    public Cart createCart(Cart cart, String token) {
        User user = userService.getCurrentUser(token).get();

        cart.setUser(user);
        cart.setCreatedAt(new Date());
		return cartRepository.save(cart);
    }
    
    public Optional<Cart> getCartById(String userId) {
    	
        Optional<Cart> optionalCart = cartRepository.findByUserUserId(userId);         
        return optionalCart;
    }
    
    public void deleteCart(String cartId,String token) {
        cartRepository.deleteById(cartId);
    }
  
 }
     