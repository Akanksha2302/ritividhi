package com.dev.ritividhi.repository;

import com.dev.ritividhi.models.OnlinePuja;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OnlinePujaRepository extends MongoRepository<OnlinePuja, String> {
}