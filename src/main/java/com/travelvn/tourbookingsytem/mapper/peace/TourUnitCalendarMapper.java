package com.travelvn.tourbookingsytem.mapper.peace;

import com.travelvn.tourbookingsytem.dto.response.peace.TourUnitCalendarResponse;
import com.travelvn.tourbookingsytem.model.TourUnit;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TourUnitCalendarMapper {
    TourUnitCalendarResponse tourUnitCalendarResponse(TourUnit tourUnit);
}
