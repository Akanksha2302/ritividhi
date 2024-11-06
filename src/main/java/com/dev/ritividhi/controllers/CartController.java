package com.dev.ritividhi.controllers;

import com.dev.ritividhi.models.Cart;
import com.dev.ritividhi.models.User;
import com.dev.ritividhi.services.CartService;
import com.dev.ritividhi.utils.CommonUtils;
import com.dev.ritividhi.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ritividhi/cart")
public class CartController {
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private UserService userService;

    @GetMapping
    public ResponseEntity<Cart> getCartByUserId(@RequestHeader("Authorization") String bearerToken) {
    	String token = CommonUtils.extractBearerToken(bearerToken);
		Optional<User> currentUser = userService.getCurrentUser(token);
		String userId= currentUser.get().getUserId();
		
        return cartService.getCartById(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Cart> createCart(@RequestBody Cart cart, @RequestHeader("Authorization") String bearerToken) {
    	String token = CommonUtils.extractBearerToken(bearerToken);
        Cart createdCart = cartService.createCart(cart,token);
        return ResponseEntity.ok(createdCart);
    }

//    @PutMapping
//    public ResponseEntity<Cart> updateCart(@RequestHeader("Authorization") String bearerToken, @RequestBody Cart cartDetails) {
//        try {
//        	String token = CommonUtils.extractBearerToken(bearerToken);
//    		Optional<User> currentUser = userService.getCurrentUser(token);
//    		String userId= currentUser.get().getUserId();
//            Cart updatedCart = cartService.updateCart(userId, cartDetails);
//            return ResponseEntity.ok(updatedCart);
//        } catch (RuntimeException e) {
//            return ResponseEntity.notFound().build();
//        }
//  }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCart(@PathVariable("id") String id,@RequestHeader(name="Authorization") String token) {
    	String bearerToken = CommonUtils.extractBearerToken(token);
    	cartService.deleteCart(id,bearerToken);
        return ResponseEntity.noContent().build(); // Responds with 204 No Content
    }
}
