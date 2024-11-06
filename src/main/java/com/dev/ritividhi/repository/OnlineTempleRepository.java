package com.dev.ritividhi.repository;

import com.dev.ritividhi.models.OnlineTemple;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OnlineTempleRepository extends MongoRepository<OnlineTemple, String> {
}