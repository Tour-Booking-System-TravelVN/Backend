package com.travelvn.tourbookingsytem.mapper;

import com.travelvn.tourbookingsytem.dto.request.CustomerRequest;
import com.travelvn.tourbookingsytem.dto.request.TourRatingRequest;
import com.travelvn.tourbookingsytem.dto.response.TourRatingResponse;
import com.travelvn.tourbookingsytem.model.Customer;
import com.travelvn.tourbookingsytem.model.TourRating;
import org.mapstruct.Mapper;

//@Component
@Mapper(componentModel = "spring")
public interface TourRatingMapper {
    TourRating toTourRating(TourRatingRequest tourRatingRequest);
    TourRating toTourRating(TourRatingResponse tourRatingResponse);

    TourRatingRequest toTourRatingRequest(TourRating tourRating);
    TourRatingResponse toTourRatingResponse(TourRating tourRating);
}
