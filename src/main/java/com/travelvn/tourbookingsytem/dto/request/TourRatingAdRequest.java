package com.travelvn.tourbookingsytem.dto.request;

import com.travelvn.tourbookingsytem.model.Customer;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString

public class TourRatingAdRequest {

    private Integer id;

    private TourUnitAdRequest tourUnit;

    private AdministratorAdRequest administrator;

    private Customer c;

    private Byte ratingValue;

    private String comment;

    private String status;

}