package com.dev.ritividhi.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.dev.ritividhi.models.Cart;

@Repository
public interface CartRepository extends MongoRepository<Cart, String> {
	
    Optional<Cart> findByUserUserId(String userId);
	
}

