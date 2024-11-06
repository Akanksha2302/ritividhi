package com.dev.ritividhi.repository;

import com.dev.ritividhi.models.Package;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PackageRepository extends MongoRepository<Package, String> {
	
	
}
