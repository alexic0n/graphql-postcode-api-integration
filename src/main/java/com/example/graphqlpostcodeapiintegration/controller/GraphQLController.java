package com.example.graphqlpostcodeapiintegration.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.example.graphqlpostcodeapiintegration.model.Postcode;
import com.example.graphqlpostcodeapiintegration.model.PostcodeResult;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class GraphQLController {

    private final WebClient webClient;

    private final String mongoUsername;

    private final String mongoPassword;

    public GraphQLController(
        @Value("${api.root.path}") String apiRootPath,
        @Value("${mongo.username}") String mongoUsername,
        @Value("${mongo.password}") String mongoPassword,
        WebClient.Builder builder
    ) {
        this.mongoUsername = mongoUsername;
        this.mongoPassword = mongoPassword;
        this.webClient = builder
            .baseUrl(apiRootPath)
            .build();
    }

    private <E> Mono<E> retrieveGenericMono(Class<E> clazz, String path, MultiValueMap<String, String> queryParams){
        return this.webClient
                    .get()
                    .uri(uriBuilder -> uriBuilder
                        .path(path)
                        .queryParams(queryParams)
                        .build()
                    )
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(clazz)
                    .onErrorResume(WebClientResponseException.NotFound.class, notFound -> Mono.empty())
                    .onErrorMap(e -> new RuntimeException(Arrays.toString(e.getStackTrace())));
    }

    private <E> Flux<E> retrieveGenericFlux(Class<E> clazz, String path, MultiValueMap<String, String> queryParams){
        return this.webClient
                    .get()
                    .uri(uriBuilder -> uriBuilder
                        .path(path)
                        .queryParams(queryParams)
                        .build()
                    )
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToFlux(clazz)
                    .onErrorResume(WebClientResponseException.NotFound.class, notFound -> Mono.empty())
                    .onErrorMap(e -> new RuntimeException(Arrays.toString(e.getStackTrace())));
    }

    @QueryMapping
    public Mono<Postcode> postcode(@Argument String postcode){
        return retrieveGenericMono(PostcodeResult.class, "postcodes/" + postcode, null)
            .map(PostcodeResult::getResult);
    }
    

    // @QueryMapping
    // public Flux<Postcode> postcodes(@Argument List<String> postcodes){
        
    // }
}
