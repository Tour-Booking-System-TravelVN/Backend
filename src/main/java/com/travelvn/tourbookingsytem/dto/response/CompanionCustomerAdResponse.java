package com.travelvn.tourbookingsytem.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CompanionCustomerAdResponse {
    private Integer id;

    private BookingResponse booking;

    private CustomerAdResponse c;

}