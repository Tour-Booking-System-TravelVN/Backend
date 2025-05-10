package com.travelvn.tourbookingsytem.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class TourProgramAdRequest {

    private Integer id;

    private TourAdRequest tour;

    private String locations;

    private Byte day;

    private String mealDescription;

    private String desciption;

}