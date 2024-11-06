package com.dev.ritividhi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.dev.ritividhi.models.CartItem;

@Repository
public interface CartItemRepository extends MongoRepository<CartItem, String> {
	
}
