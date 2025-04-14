package com.travelvn.tourbookingsytem.dto.response.peace;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@ToString
public class TourCalendarResponse {
    private Integer month;
    private Integer year;

    public TourCalendarResponse(){};

    public TourCalendarResponse(Integer month, Integer year) {
        this.month = month;
        this.year = year;
    }
}

