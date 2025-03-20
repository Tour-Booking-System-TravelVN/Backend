package com.travelvn.tourbookingsytem.mapper;

import com.travelvn.tourbookingsytem.dto.request.CustomerRequest;
import com.travelvn.tourbookingsytem.dto.request.TourOperatorRequest;
import com.travelvn.tourbookingsytem.dto.response.TourOperatorResponse;
import com.travelvn.tourbookingsytem.model.Customer;
import com.travelvn.tourbookingsytem.model.TourOperator;
import org.mapstruct.Mapper;

//@Component
@Mapper(componentModel = "spring")
public interface TourOperatorMapper {
    TourOperator toTourOperator(TourOperatorRequest tourOperatorRequest);
    TourOperatorRequest toTourOperatorRequest(TourOperator tourOperator);

    TourOperator toTourOperator(TourOperatorResponse tourOperatorResponse);
    TourOperatorResponse toTourOperatorResponse(TourOperator tourOperator);
}
