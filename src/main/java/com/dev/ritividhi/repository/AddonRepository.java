package com.dev.ritividhi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.dev.ritividhi.models.Addon;

@Repository
public interface AddonRepository extends MongoRepository<Addon, String> {

}
