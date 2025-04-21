package com.travelvn.tourbookingsytem.mapper;

import com.travelvn.tourbookingsytem.dto.request.CustomerRequest;
import com.travelvn.tourbookingsytem.dto.request.TourUnitRequest;
import com.travelvn.tourbookingsytem.dto.response.TourUnitResponse;
import com.travelvn.tourbookingsytem.model.Customer;
import com.travelvn.tourbookingsytem.model.TourOperator;
import com.travelvn.tourbookingsytem.model.TourUnit;
import org.mapstruct.Mapper;

//@Component
@Mapper(componentModel = "spring")
public interface TourUnitMapper {
    TourUnit toTourUnit(TourUnitRequest tourUnitRequest);
//    TourUnitRequest toTourUnitRequest(TourUnit tourUnit);
default TourOperator map(Integer id) {
    if (id == null) {
        return null;
    }
    TourOperator tourOperator = new TourOperator();
    tourOperator.setId(id); // Gán ID cho đối tượng TourOperator
    return tourOperator;
}
//    TourUnit toTourUnit(TourUnitResponse tourUnitResponse);
    TourUnitResponse toTourUnitResponse(TourUnit tourUnit);
}
