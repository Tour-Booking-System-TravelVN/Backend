package com.travelvn.tourbookingsytem.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BookingStatus {
    P("Prepare"),
    O("On-going"),
    C("Cancle"),
    D("Done"),
    W("Wait"),
    E("Error"),
    H("Holding")
    ;
    private String statusName;
}
