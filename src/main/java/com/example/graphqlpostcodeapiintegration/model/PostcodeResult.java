package com.example.graphqlpostcodeapiintegration.model;

import lombok.Data;

@Data
public class PostcodeResult {

    private final Integer status;
    private final Postcode result;
    
}
