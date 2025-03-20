package com.travelvn.tourbookingsytem.dto.response;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString

public class TourResponse {

    private String tourId;

    private CatergoryResponse category;

    private TourOperatorResponse tourOperator;

    private TourOperatorResponse lastUpdatedOperator;

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