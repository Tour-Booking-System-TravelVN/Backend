package com.travelvn.tourbookingsytem.dto.response;

import com.travelvn.tourbookingsytem.model.Image;
import com.travelvn.tourbookingsytem.model.TourProgram;
import lombok.*;

import java.time.Instant;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString

public class TourResponse {

    private String tourId;

    private CategoryResponse category;

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

    private Set<TourProgram> tourProgramSet;

    private Set<ImageResponse> imageSet;

//    private Set<TourUnitResponse> tourUnitSet;

    private String firstImageUrl; // Thêm trường này để chứa URL của ảnh đầu tiên
}