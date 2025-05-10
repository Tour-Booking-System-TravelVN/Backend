package com.travelvn.tourbookingsytem.mapper;

import com.travelvn.tourbookingsytem.dto.request.FestivalRequest;
import com.travelvn.tourbookingsytem.dto.response.FestivalResponse;
import com.travelvn.tourbookingsytem.model.Festival;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface FestivalMapper {
    FestivalMapper INSTANCE = Mappers.getMapper(FestivalMapper.class);

    Festival toEntity(FestivalRequest festivalRequest);

    FestivalResponse toResponse(Festival festival);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromRequest(FestivalRequest festivalRequest, @MappingTarget Festival festival);
}