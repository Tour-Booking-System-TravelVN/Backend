package com.travelvn.tourbookingsytem.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class TourRequest {
    @NotNull(message = "Category ID must not be null")
    private Integer categoryId;

    @NotNull(message = "Tour Operator ID must not be null")
    private Integer tourOperatorId;

    private Integer lastUpdatedOperator;

    @NotBlank(message = "Tour name must not be blank")
    private String tourName;

    @NotBlank(message = "Duration must not be blank")
    private String duration;

    @NotBlank(message = "Vehicle must not be blank")
    private String vehicle;

    @NotBlank(message = "Target audience must not be blank")
    private String targetAudience;

    @NotBlank(message = "Departure place must not be blank")
    private String departurePlace;

    @NotBlank(message = "Places to visit must not be blank")
    private String placesToVisit;

    @NotBlank(message = "Cuisine must not be blank")
    private String cuisine;

    @NotBlank(message = "Ideal time must not be blank")
    private String idealTime;

    @Size(max = 255, message = "Description must not exceed 255 characters")
    private String description;

    @NotNull(message = "Created time must not be null")
    private Instant createdTime;

    private Instant lastUpdatedTime;

    @NotBlank(message = "Inclusions must not be blank")
    private String inclusions;

    @NotBlank(message = "Exclusions must not be blank")
    private String exclusions;
}