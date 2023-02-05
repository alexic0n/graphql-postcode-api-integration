package com.example.graphqlpostcodeapiintegration.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.example.graphqlpostcodeapiintegration.service.IPostcodeService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class GraphQLController {

    private WebClient webClient;

    @Autowired
    private IPostcodeService postcodeService;

    public GraphQLController(
        @Value("${api.root.path}") String apiRootPath,
        WebClient.Builder builder
    ) {
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

    @QueryMapping
    public Mono<Postcode> postcode(@Argument String postcode){
        return postcodeService
            .findById(postcode)
            .switchIfEmpty(
                Mono.defer(() ->
                    retrieveGenericMono(PostcodeResult.class, "postcodes/" + postcode, null)
                    .map(PostcodeResult::getResult)
                    .flatMap(postcodeService::insert)
                )
            );
    }
    

    @QueryMapping
    public Flux<Postcode> postcodes(){
        return postcodeService.findAll();
    }
}
