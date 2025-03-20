package com.travelvn.tourbookingsytem.dto.request;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString

public class TourRequest {

    private String tourId;

    private CatergoryRequest category;

    private TourOperatorRequest tourOperator;

    private TourOperatorRequest lastUpdatedOperator;

    private String tourName;

    private String duration;

    private String vehicle;

    private String targetAudience;

    private String departurePlace;

    private String placesToVisit;

    private String cuisine;

    private String idealTime;

    private String description;

    private Instant createdTime;

    private Instant lastUpdatedTime;

    private String inclusions;

    private String exclusions;

}