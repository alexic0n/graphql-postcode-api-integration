package com.example.graphqlpostcodeapiintegration.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class PostcodeCodes {
    @JsonProperty("admin_district")
    private final String adminDistrict;
    @JsonProperty("admin_county")
    private final String adminCounty;
    @JsonProperty("admin_ward")
    private final String adminWard;
    private final String parish;
    @JsonProperty("parliamentary_constituency")
    private final String parliamentaryConstituency;
    private final String ccg;
    @JsonProperty("ccg_id")
    private final String ccgId;
    private final String ced;
    private final String nuts;
    private final String lsoa;
    private final String msoa;
    private final String lau2;
    private final String pfa;
}
