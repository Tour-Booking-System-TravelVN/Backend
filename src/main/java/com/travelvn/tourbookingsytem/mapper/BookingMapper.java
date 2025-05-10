package com.travelvn.tourbookingsytem.mapper;

import com.travelvn.tourbookingsytem.dto.request.BookingRequest;
import com.travelvn.tourbookingsytem.dto.response.BookingResponse;
import com.travelvn.tourbookingsytem.model.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {CustomerMapper.class, TourUnitMapper.class})
public interface BookingMapper {
    BookingMapper INSTANCE = Mappers.getMapper(BookingMapper.class);

    @Mapping(source = "CId", target = "c.id")
    @Mapping(source = "tourUnitId", target = "tourUnit.tourUnitId")
    Booking toEntity(BookingRequest bookingRequest);

    @Mapping(source = "c", target = "c")
    @Mapping(source = "tourUnit", target = "tourUnit")
    BookingResponse toResponse(Booking booking);

    @Mapping(target = "bookingId", ignore = true)
    @Mapping(target = "c", ignore = true)
    @Mapping(target = "tourUnit", ignore = true)
    void updateEntityFromRequest(BookingRequest bookingRequest, @MappingTarget Booking booking);
}