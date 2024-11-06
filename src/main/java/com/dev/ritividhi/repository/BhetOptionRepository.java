package com.dev.ritividhi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.dev.ritividhi.models.BhetOptions;

public interface BhetOptionRepository extends MongoRepository<BhetOptions, String>{

}
