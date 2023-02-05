package com.example.graphqlpostcodeapiintegration.controller;

import java.util.Arrays;

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

import reactor.core.publisher.Mono;

@Controller
public class GraphQLController {

    private final WebClient webClient;

    public GraphQLController(
        @Value("${api.root.path}") String apiRootPath,
        WebClient.Builder builder
    ) {
        this.webClient = builder
            .baseUrl(apiRootPath)
            .build();
    }

    private <E> Mono<E> retrieveGeneric(Class<E> clazz, String path, MultiValueMap<String, String> queryParams){
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
        return retrieveGeneric(PostcodeResult.class, "postcodes/" + postcode, null)
            .map(PostcodeResult::getResult);
    }
    
}
