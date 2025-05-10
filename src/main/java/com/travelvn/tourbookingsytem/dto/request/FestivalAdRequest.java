package com.travelvn.tourbookingsytem.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class FestivalAdRequest {
    private String festivalName;
    private String description;
    private Boolean displayStatus;
}