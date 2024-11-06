package com.dev.ritividhi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import com.dev.ritividhi.models.PujaSummary;

@Repository
public interface PujaSummaryRepository extends MongoRepository<PujaSummary, String> {

	List<PujaSummary> findByPujaId(String pujaId);
}