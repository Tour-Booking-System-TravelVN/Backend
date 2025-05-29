package com.travelvn.tourbookingsytem.mapper;

import com.travelvn.tourbookingsytem.dto.request.BookingRequest;
import com.travelvn.tourbookingsytem.dto.response.BookingResponse;
import com.travelvn.tourbookingsytem.mapper.lite.CompanionCustomerLiteMapper;
import com.travelvn.tourbookingsytem.model.Booking;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring", uses={CustomerAdMapper.class, TourUnitMapper.class, /*CustomerMapper.class, CompanionCustomerMapper.class,*/ CompanionCustomerLiteMapper.class, TourUnitMapper.class})
public interface BookingMapper {
    BookingMapper INSTANCE = Mappers.getMapper(BookingMapper.class);
    @Named("toEntity")
    @Mapping(source = "c", target = "c")
    @Mapping(source = "tourUnitId", target = "tourUnit.tourUnitId")
    Booking toEntity(BookingRequest bookingRequest);


    //Mapping việc đặt tour
    @Mappings({
            @Mapping(target = "c", ignore = true),
            @Mapping(target = "tourUnit", ignore = true),
            @Mapping(target = "companionCustomerSet", ignore = true)
    })
    Booking toBookingTour(BookingRequest bookingRequest);
//    Booking toBooking(BookingResponse bookingResponse);
    @Named("toResponse")
    @Mapping(source = "c", target = "c")
    @Mapping(source = "tourUnit", target = "tourUnit")
    BookingResponse toResponse(Booking booking);

    @Mapping(target = "bookingId", ignore = true)
    @Mapping(target = "c", ignore = true)
    @Mapping(target = "tourUnit", ignore = true)
    void updateEntityFromRequest(BookingRequest bookingRequest, @MappingTarget Booking booking);
    @Mappings({
            @Mapping(target = "c", ignore = true),
            @Mapping(target = "tourUnit", source = "tourUnit", qualifiedByName = "toTourUnitDTOFound")
    })
    BookingResponse toBookingResponse(Booking booking);
//    BookingRequest toBookingRequest(Booking booking);
}