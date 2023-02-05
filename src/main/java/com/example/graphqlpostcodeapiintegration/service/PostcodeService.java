package com.example.graphqlpostcodeapiintegration.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.graphqlpostcodeapiintegration.model.Postcode;
import com.example.graphqlpostcodeapiintegration.repository.PostcodeRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PostcodeService implements IPostcodeService {

    @Autowired
    private PostcodeRepository postcodeRepository;

    @Override
    public Mono<Postcode> insert(Postcode postcode) {
        return postcodeRepository.insert(postcode);
    }

    @Override
    public Flux<Postcode> saveAll(List<Postcode> postcodes) {
        return postcodeRepository.insert(postcodes);
    }

    @Override
    public Mono<Postcode> findById(String id) {
        return postcodeRepository.findById(id);
    }

    @Override
    public Flux<Postcode> findAll() {
        return postcodeRepository.findAll();
    }

    @Override
    public Mono<Void> deleteAll() {
        return postcodeRepository.deleteAll();
    }
    
}
