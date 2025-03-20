package com.travelvn.tourbookingsytem.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class FestivalResponse {

    private Integer id;

    private String festivalName;

    private String description;

    private Boolean displayStatus = false;

}