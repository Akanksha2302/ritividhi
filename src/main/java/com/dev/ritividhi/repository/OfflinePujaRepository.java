package com.dev.ritividhi.repository;


import java.util.List;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.dev.ritividhi.models.OfflinePuja;

@Repository
public interface OfflinePujaRepository extends MongoRepository<OfflinePuja, String> {

 List<OfflinePuja> findAll();

}