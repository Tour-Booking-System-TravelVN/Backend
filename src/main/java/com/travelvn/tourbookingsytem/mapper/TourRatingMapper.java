package com.travelvn.tourbookingsytem.mapper;

import com.travelvn.tourbookingsytem.dto.response.TourRatingResponse;
import com.travelvn.tourbookingsytem.model.TourRating;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TourRatingMapper {
    TourRatingMapper INSTANCE = Mappers.getMapper(TourRatingMapper.class);

    TourRatingResponse toResponse(TourRating tourRating);
}