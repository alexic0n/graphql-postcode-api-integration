package com.example.graphqlpostcodeapiintegration.service;

import java.util.List;

import com.example.graphqlpostcodeapiintegration.model.Postcode;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IPostcodeService {
    Mono<Postcode> insert(Postcode postcode);
    Flux<Postcode> saveAll(List<Postcode> postcodes);
    Mono<Postcode> findById(String id);
    Flux<Postcode> findAll();
    Mono<Void> deleteAll();

}
