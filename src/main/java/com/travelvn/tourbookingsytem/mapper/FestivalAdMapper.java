package com.travelvn.tourbookingsytem.mapper;

import com.travelvn.tourbookingsytem.dto.request.FestivalAdRequest;
import com.travelvn.tourbookingsytem.dto.response.FestivalAdResponse;
import com.travelvn.tourbookingsytem.model.Festival;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface FestivalAdMapper {
    FestivalAdMapper INSTANCE = Mappers.getMapper(FestivalAdMapper.class);

    Festival toEntity(FestivalAdRequest festivalAdRequest);

    FestivalAdResponse toResponse(Festival festival);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromRequest(FestivalAdRequest festivalAdRequest, @MappingTarget Festival festival);
}