package com.travelvn.tourbookingsytem.dto.request;

import lombok.Getter;

@Getter
public class TourRatingStatusAdUpdate {
    private String status;

    public void setStatus(String status) {
        this.status = status;
    }
}
