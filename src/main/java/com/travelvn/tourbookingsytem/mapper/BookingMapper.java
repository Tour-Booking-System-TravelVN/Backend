package com.travelvn.tourbookingsytem.mapper;

import com.travelvn.tourbookingsytem.dto.request.BookingRequest;
import com.travelvn.tourbookingsytem.dto.response.BookingResponse;
import com.travelvn.tourbookingsytem.mapper.lite.CompanionCustomerLiteMapper;
import com.travelvn.tourbookingsytem.model.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses={/*CustomerMapper.class, CompanionCustomerMapper.class,*/ CompanionCustomerLiteMapper.class, TourUnitMapper.class})
public interface BookingMapper {

    //Mapping việc đặt tour
    @Mappings({
            @Mapping(target = "c", ignore = true),
            @Mapping(target = "tourUnit", ignore = true),
            @Mapping(target = "companionCustomerSet", ignore = true)
    })
    Booking toBookingTour(BookingRequest bookingRequest);
//    Booking toBooking(BookingResponse bookingResponse);

    @Mappings({
            @Mapping(target = "c", ignore = true),
            @Mapping(target = "tourUnit", source = "tourUnit", qualifiedByName = "toTourUnitDTOFound")
    })
    BookingResponse toBookingResponse(Booking booking);
//    BookingRequest toBookingRequest(Booking booking);
}
