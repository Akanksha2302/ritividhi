package com.dev.ritividhi.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.dev.ritividhi.models.Order;

public interface OrderRepository extends MongoRepository<Order, String> {

	void save(Optional<Order> order);
}