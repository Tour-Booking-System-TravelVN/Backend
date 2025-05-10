package com.travelvn.tourbookingsytem.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class TourRatingAdResponse {
    private Integer id;
    private String tourUnitId;
    private Integer customerId;
    private Byte ratingValue;
    private String comment;
    private String status;
}