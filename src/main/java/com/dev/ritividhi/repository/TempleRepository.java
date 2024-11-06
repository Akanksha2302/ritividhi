package com.dev.ritividhi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.dev.ritividhi.models.Temple;
import com.dev.ritividhi.models.TemplePuja;

public interface TempleRepository extends MongoRepository<Temple, String> { 
	Temple findByTempleId(String templeId);
//	TemplePuja deleteByTempleId(String templeId);
}
