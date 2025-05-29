package com.travelvn.tourbookingsytem.dto.response;

import com.travelvn.tourbookingsytem.model.Customer;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CompanionCustomerResponse {
    private Integer id;

    private BookingResponse booking;

//    private CustomerResponse c;

}