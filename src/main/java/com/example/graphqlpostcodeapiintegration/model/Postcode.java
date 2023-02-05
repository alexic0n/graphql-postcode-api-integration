package com.example.graphqlpostcodeapiintegration.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@Document(value="postcodes")
public class Postcode {

    @Id
    private final String postcode;
    private final Integer quality;
    private final Integer eastings;
    private final Integer northings;
    private final String country;
    @JsonProperty("nhs_ha")
    private final String nhsHa;
    @JsonProperty("date_of_introduction")
    private final String dateOfIntroduction;
    private final Double longitude;
    private final Double latitude;
    @JsonProperty("european_electoral_region")
    private final String europeanElectoralRegion;
    @JsonProperty("primary_care_trust")
    private final String primaryCareTrust;
    private final String region;
    private final String lsoa;
    private final String msoa;
    private final String incode;
    private final String outcode;
    @JsonProperty("parliamentary_constituency")
    private final String parliamentaryConstituency;
    @JsonProperty("admin_district")
    private final String adminDistrict;
    private final String parish;
    @JsonProperty("admin_county")
    private final String adminCounty;
    @JsonProperty("admin_ward")
    private final String adminWard;
    private final String ced;
    private final String ccg;
    private final String nutsl;
    private final String pfa;
    private final PostcodeCodes codes;

    
}
