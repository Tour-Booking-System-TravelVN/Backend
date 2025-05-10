package com.travelvn.tourbookingsytem.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CompanionCustomerAdRequest {
    private Integer id;

    private BookingRequest booking;

    private CustomerAdRequest c;

}