package com.travelvn.tourbookingsytem.mapper;

import com.travelvn.tourbookingsytem.dto.request.TourGuideRequest;
import com.travelvn.tourbookingsytem.dto.response.TourGuideResponse;
import com.travelvn.tourbookingsytem.model.TourGuide;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TourGuideMapper {
    TourGuideMapper INSTANCE = Mappers.getMapper(TourGuideMapper.class);

    TourGuide toEntity(TourGuideRequest tourGuideRequest);

    TourGuideResponse toResponse(TourGuide tourGuide);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromRequest(TourGuideRequest tourGuideRequest, @MappingTarget TourGuide tourGuide);
}