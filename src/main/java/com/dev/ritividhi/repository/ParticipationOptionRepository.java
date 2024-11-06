package com.dev.ritividhi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.dev.ritividhi.models.ParticipationOption;

public interface ParticipationOptionRepository extends MongoRepository<ParticipationOption, String> {

}
