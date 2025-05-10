package com.travelvn.tourbookingsytem.mapper;

import com.travelvn.tourbookingsytem.dto.response.TourRatingAdResponse;
import com.travelvn.tourbookingsytem.model.TourRating;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TourRatingMapper {
    TourRatingMapper INSTANCE = Mappers.getMapper(TourRatingMapper.class);

    TourRatingAdResponse toResponse(TourRating tourRating);
}