package com.travelvn.tourbookingsytem.mapper;

import com.travelvn.tourbookingsytem.dto.request.TourOperatorRequest;
import com.travelvn.tourbookingsytem.dto.response.TourOperatorResponse;
import com.travelvn.tourbookingsytem.model.TourOperator;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TourOperatorMapper {
    TourOperatorMapper INSTANCE = Mappers.getMapper(TourOperatorMapper.class);

    TourOperator toEntity(TourOperatorRequest tourOperatorRequest);

    TourOperatorResponse toResponse(TourOperator tourOperator);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromRequest(TourOperatorRequest tourOperatorRequest, @MappingTarget TourOperator tourOperator);
}