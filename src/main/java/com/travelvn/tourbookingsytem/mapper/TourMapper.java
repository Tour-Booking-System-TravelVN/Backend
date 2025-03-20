package com.travelvn.tourbookingsytem.mapper;

import com.travelvn.tourbookingsytem.dto.request.CustomerRequest;
import com.travelvn.tourbookingsytem.dto.request.TourRequest;
import com.travelvn.tourbookingsytem.dto.response.TourResponse;
import com.travelvn.tourbookingsytem.model.Customer;
import com.travelvn.tourbookingsytem.model.Tour;
import org.mapstruct.Mapper;

//@Component
@Mapper(componentModel = "spring")
public interface TourMapper {
    Tour toTour(TourRequest tourRequest);
    Tour toTour(TourResponse tourResponse);

    TourResponse toTourResponse(Tour tour);
    TourRequest toTourRequest(Tour tour);
}
