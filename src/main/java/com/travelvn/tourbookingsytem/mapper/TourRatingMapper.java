package com.travelvn.tourbookingsytem.mapper;

import com.travelvn.tourbookingsytem.dto.request.CustomerRequest;
import com.travelvn.tourbookingsytem.dto.request.TourRatingRequest;
import com.travelvn.tourbookingsytem.dto.response.TourRatingResponse;
import com.travelvn.tourbookingsytem.model.Customer;
import com.travelvn.tourbookingsytem.model.TourOperator;
import com.travelvn.tourbookingsytem.model.TourRating;
import org.mapstruct.Mapper;

//@Component
@Mapper(componentModel = "spring")
public interface TourRatingMapper {
    TourRating toTourRating(TourRatingRequest tourRatingRequest);

    // Phương thức ánh xạ từ Integer sang TourOperator
    default TourOperator map(Integer id) {
        if (id == null) {
            return null;
        }
        TourOperator tourOperator = new TourOperator();
        tourOperator.setId(id); // Gán ID cho đối tượng TourOperator
        return tourOperator;
    }

    TourRatingResponse toTourRatingResponse(TourRating tourRating);
}
