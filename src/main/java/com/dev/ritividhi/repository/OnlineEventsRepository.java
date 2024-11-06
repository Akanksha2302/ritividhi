package com.dev.ritividhi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.dev.ritividhi.models.OnlineEvents;

public interface OnlineEventsRepository extends MongoRepository<OnlineEvents, String> {

}
