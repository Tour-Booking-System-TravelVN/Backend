package com.travelvn.tourbookingsytem.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
public class TourAdRequest {
    private Integer categoryId;
    private Integer tourOperatorId;
    private Integer lastUpdatedOperator;
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
    private List<MultipartFile> images;
}