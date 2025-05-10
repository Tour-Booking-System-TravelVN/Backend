package com.travelvn.tourbookingsytem.mapper;

import com.travelvn.tourbookingsytem.dto.request.TourUnitRequest;
import com.travelvn.tourbookingsytem.dto.response.TourUnitResponse;
import com.travelvn.tourbookingsytem.model.TourUnit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TourUnitMapper {
    TourUnitMapper INSTANCE = Mappers.getMapper(TourUnitMapper.class);

    @Mapping(target = "festival", ignore = true)
    @Mapping(target = "tour", ignore = true)
    @Mapping(target = "discount", ignore = true)
    @Mapping(target = "tourOperator", ignore = true)
    @Mapping(target = "lastUpdatedOperator", ignore = true)
    @Mapping(target = "guideSet", ignore = true)
    @Mapping(target = "bookingSet", ignore = true)
    @Mapping(target = "ratingSet", ignore = true)
    TourUnit toEntity(TourUnitRequest tourUnitRequest);

    TourUnitResponse toResponse(TourUnit tourUnit);

    @Mapping(target = "tourUnitId", ignore = true)
    @Mapping(target = "festival", ignore = true)
    @Mapping(target = "tour", ignore = true)
    @Mapping(target = "discount", ignore = true)
    @Mapping(target = "tourOperator", ignore = true)
    @Mapping(target = "lastUpdatedOperator", ignore = true)
    @Mapping(target = "guideSet", ignore = true)
    @Mapping(target = "bookingSet", ignore = true)
    @Mapping(target = "ratingSet", ignore = true)
    void updateEntityFromRequest(TourUnitRequest tourUnitRequest, @MappingTarget TourUnit tourUnit);
}