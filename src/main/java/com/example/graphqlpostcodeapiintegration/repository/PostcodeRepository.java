package com.example.graphqlpostcodeapiintegration.repository;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.example.graphqlpostcodeapiintegration.model.Postcode;

@Configuration
public interface PostcodeRepository extends ReactiveMongoRepository<Postcode, String> {
    
}
