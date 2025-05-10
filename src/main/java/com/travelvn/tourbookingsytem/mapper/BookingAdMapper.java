package com.travelvn.tourbookingsytem.mapper;

import com.travelvn.tourbookingsytem.dto.request.BookingAdRequest;
import com.travelvn.tourbookingsytem.dto.response.BookingAdResponse;
import com.travelvn.tourbookingsytem.model.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BookingAdMapper {
    BookingAdMapper INSTANCE = Mappers.getMapper(BookingAdMapper.class);

    @Mapping(target = "c", ignore = true)
    @Mapping(target = "tourUnit", ignore = true)
    @Mapping(target = "companionCustomerSet", ignore = true)
    Booking toEntity(BookingAdRequest bookingAdRequest);

    @Mapping(source = "c.id", target = "cId")
    @Mapping(source = "tourUnit.tourUnitId", target = "tourUnitId")
    BookingAdResponse toResponse(Booking booking);

    @Mapping(target = "c", ignore = true)
    @Mapping(target = "tourUnit", ignore = true)
    @Mapping(target = "companionCustomerSet", ignore = true)
    @Mapping(target = "bookingId", ignore = true)
    void updateEntityFromRequest(BookingAdRequest bookingAdRequest, @MappingTarget Booking booking);
}