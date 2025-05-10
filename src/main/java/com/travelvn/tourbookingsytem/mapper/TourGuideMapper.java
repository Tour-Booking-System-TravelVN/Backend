package com.travelvn.tourbookingsytem.mapper;

import com.travelvn.tourbookingsytem.dto.request.TourGuideAdRequest;
import com.travelvn.tourbookingsytem.dto.response.TourGuideAdResponse;
import com.travelvn.tourbookingsytem.model.TourGuide;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TourGuideMapper {
    TourGuideMapper INSTANCE = Mappers.getMapper(TourGuideMapper.class);

    TourGuide toEntity(TourGuideAdRequest tourGuideAdRequest);

    TourGuideAdResponse toResponse(TourGuide tourGuide);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromRequest(TourGuideAdRequest tourGuideAdRequest, @MappingTarget TourGuide tourGuide);
}