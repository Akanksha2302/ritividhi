package com.dev.ritividhi.repository;

import com.dev.ritividhi.models.Temple;
import com.dev.ritividhi.models.TemplePujaDetails;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface TemplePujaDetailsRepository extends MongoRepository<TemplePujaDetails, String> {
//    List<TemplePujaDetails> findByTemple(String templeId);
//    List<TemplePujaDetails> findByPujaId(String pujaId); // Example method


}
