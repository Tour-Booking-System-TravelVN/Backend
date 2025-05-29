package com.travelvn.tourbookingsytem.mapper;

import com.travelvn.tourbookingsytem.dto.request.TourRatingRequest;
import com.travelvn.tourbookingsytem.dto.response.TourRatingAdResponse;
import com.travelvn.tourbookingsytem.dto.response.TourRatingResponse;
import com.travelvn.tourbookingsytem.model.TourRating;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TourRatingMapper {

    TourRating toTourRating(TourRatingRequest tourRatingRequest);
//    TourRating toTourRating(TourRatingResponse tourRatingResponse);
    TourRatingMapper INSTANCE = Mappers.getMapper(TourRatingMapper.class);

//    TourRatingRequest toTourRatingRequest(TourRating tourRating);
    @Mappings({
            @Mapping(target = "fullName",  expression = "java(rating.getC().getFirstname() + \" \" + rating.getC().getLastname())")
    })
    TourRatingResponse toTourRatingResponse(TourRating rating);
    TourRatingAdResponse toResponse(TourRating tourRating);
}