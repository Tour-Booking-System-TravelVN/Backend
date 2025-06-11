package com.travelvn.tourbookingsytem.dto.response;

import lombok.*;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class TourAdResponse {
    private String tourId;
    private Integer categoryId;
    private Integer tourOperatorId;
    private Integer lastUpdatedOperatorId;
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
    private List<String> imageUrls;
}