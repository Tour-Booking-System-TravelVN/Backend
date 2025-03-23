package com.travelvn.tourbookingsytem.mapper;

import com.travelvn.tourbookingsytem.dto.request.BookingRequest;
import com.travelvn.tourbookingsytem.dto.response.BookingResponse;
import com.travelvn.tourbookingsytem.model.Booking;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses={TourUnitMapper.class, CustomerMapper.class})
public interface BookingMapper {
    Booking toBooking(BookingRequest bookingRequest);
//    Booking toBooking(BookingResponse bookingResponse);

    BookingResponse toBookingResponse(Booking booking);
//    BookingRequest toBookingRequest(Booking booking);
}
