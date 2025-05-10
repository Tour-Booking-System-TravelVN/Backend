package com.travelvn.tourbookingsytem.mapper;

import com.travelvn.tourbookingsytem.dto.request.TourAdRequest;
import com.travelvn.tourbookingsytem.dto.response.TourAdResponse;
import com.travelvn.tourbookingsytem.model.Tour;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TourMapper {
    TourMapper INSTANCE = Mappers.getMapper(TourMapper.class);


    @Mapping(target = "category", ignore = true)
    @Mapping(target = "tourOperator", ignore = true)
    @Mapping(target = "lastUpdatedOperator", ignore = true)
    Tour toEntity(TourAdRequest tourAdRequest);

    TourAdResponse toResponse(Tour tour);

    @Mapping(target = "tourId", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "tourOperator", ignore = true)
    @Mapping(target = "lastUpdatedOperator", ignore = true)
    void updateEntityFromRequest(TourAdRequest tourAdRequest, @MappingTarget Tour tour);
}
