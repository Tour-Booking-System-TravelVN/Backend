package com.travelvn.tourbookingsytem.mapper;

import com.travelvn.tourbookingsytem.dto.request.TourUnitAdRequest;
import com.travelvn.tourbookingsytem.dto.response.TourUnitAdResponse;
import com.travelvn.tourbookingsytem.dto.response.TourUnitResponse;
import com.travelvn.tourbookingsytem.model.TourUnit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

@Named("TourUnitMapper")
@Mapper(componentModel = "spring", uses = {TourMapper.class})
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
    TourUnit toEntity(TourUnitAdRequest tourUnitAdRequest);

    TourUnitAdResponse toResponse(TourUnit tourUnit);

    @Mapping(target = "tourUnitId", ignore = true)
    @Mapping(target = "festival", ignore = true)
    @Mapping(target = "tour", ignore = true)
    @Mapping(target = "discount", ignore = true)
    @Mapping(target = "tourOperator", ignore = true)
    @Mapping(target = "lastUpdatedOperator", ignore = true)
    @Mapping(target = "guideSet", ignore = true)
    @Mapping(target = "bookingSet", ignore = true)
    @Mapping(target = "ratingSet", ignore = true)
    void updateEntityFromRequest(TourUnitAdRequest tourUnitAdRequest, @MappingTarget TourUnit tourUnit);
//    TourUnit toTourUnit(TourUnitResponse tourUnitResponse);
//    TourUnitResponse toTourUnitResponse(TourUnit tourUnit);

    @Named("toTourUnitDTOFound")
    @Mappings({
            @Mapping(target = "tourOperator", ignore = true),
            @Mapping(target = "lastUpdatedOperator", ignore = true),
            @Mapping(target = "createdTime", ignore = true),
            @Mapping(target = "lastUpdatedTime", ignore = true),
            @Mapping(source = "tour", target = "tour", qualifiedByName = {"TourMapper", "toTourResponseByFound"})
    })

    TourUnitResponse toTourUnitResponseByFound(TourUnit tourUnit);
}
