package com.dev.ritividhi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.dev.ritividhi.models.Temple;
import com.dev.ritividhi.models.TemplePuja;
import com.dev.ritividhi.models.TemplePujaDetails;

@Repository
public interface TemplePujaRepository extends MongoRepository<TemplePuja, String> {
	 Optional<TemplePuja> findByTempleId(String templeId);
}
