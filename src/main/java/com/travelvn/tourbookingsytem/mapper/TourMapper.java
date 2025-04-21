package com.travelvn.tourbookingsytem.mapper;

import com.travelvn.tourbookingsytem.dto.request.CustomerRequest;
import com.travelvn.tourbookingsytem.dto.request.TourRequest;
import com.travelvn.tourbookingsytem.dto.response.TourResponse;
import com.travelvn.tourbookingsytem.model.Customer;
import com.travelvn.tourbookingsytem.model.Tour;
import com.travelvn.tourbookingsytem.model.TourOperator;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TourMapper {
    Tour toTour(TourRequest tourRequest);

    // Phương thức ánh xạ từ Integer sang TourOperator
    default TourOperator map(Integer id) {
        if (id == null) {
            return null;
        }
        TourOperator tourOperator = new TourOperator();
        tourOperator.setId(id); // Gán ID cho đối tượng TourOperator
        return tourOperator;
    }

    TourResponse toTourResponse(Tour tour);
}
