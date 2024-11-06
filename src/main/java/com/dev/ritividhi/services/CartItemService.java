package com.dev.ritividhi.services;

import com.dev.ritividhi.models.Addon;
import com.dev.ritividhi.models.CartItem;
import com.dev.ritividhi.repository.AddonRepository;
import com.dev.ritividhi.repository.CartItemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartItemService {


    @Autowired
    AddonRepository addonRepository;
    @Autowired
    CartItemRepository cartItemRepository;

    public List<CartItem> getAllCartItems() {
        return cartItemRepository.findAll();
    }

    public CartItem saveCartItem(CartItem cartItem) {
    	 List<Addon> resolvedAddons = new ArrayList<>();
    	    for (Addon addon : cartItem.getAddons()) {
    	        Optional<Addon> addonOptional = addonRepository.findById("id");
    	        if (addonOptional.isPresent()) {
    	            resolvedAddons.add(addonOptional.get());
    	        } else {    
    	            System.out.println("Addon not found with ID: " + addon.getAddonId());
    	        }
    	    }  
    	    cartItem.setAddons(resolvedAddons);
    	    return cartItemRepository.save(cartItem);
    }
    
}