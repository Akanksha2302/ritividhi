package com.dev.ritividhi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.dev.ritividhi.models.Address;

public interface AddressRepository extends MongoRepository<Address, String> {


}