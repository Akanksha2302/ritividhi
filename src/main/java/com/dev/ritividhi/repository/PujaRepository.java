package com.dev.ritividhi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.dev.ritividhi.models.Puja;

@Repository
public interface PujaRepository extends MongoRepository<Puja, String> {
}

