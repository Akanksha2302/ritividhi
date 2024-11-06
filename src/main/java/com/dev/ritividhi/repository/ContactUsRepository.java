package com.dev.ritividhi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.dev.ritividhi.models.ContactUs;

public interface ContactUsRepository extends MongoRepository<ContactUs, String> {

}
