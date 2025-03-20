package com.travelvn.tourbookingsytem.mapper;

import com.travelvn.tourbookingsytem.dto.request.CustomerRequest;
import com.travelvn.tourbookingsytem.dto.request.TourGuideRequest;
import com.travelvn.tourbookingsytem.dto.response.TourGuideResponse;
import com.travelvn.tourbookingsytem.model.Customer;
import com.travelvn.tourbookingsytem.model.TourGuide;
import org.mapstruct.Mapper;

//@Component
@Mapper(componentModel = "spring")
public interface TourGuideMapper {
    TourGuide toTourGuide(TourGuideRequest tourGuideRequest);
    TourGuide toTourGuide(TourGuideResponse tourGuideResponse);

    TourGuideResponse toTourGuideResponse(TourGuide tourGuide);
    TourGuideRequest toTourGuideRequest(TourGuide tourGuideRequest);
}
