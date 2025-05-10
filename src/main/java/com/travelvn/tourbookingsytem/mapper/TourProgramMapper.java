package com.travelvn.tourbookingsytem.mapper;

import com.travelvn.tourbookingsytem.dto.request.TourProgramAdRequest;
import com.travelvn.tourbookingsytem.dto.response.TourProgramAdResponse;
import com.travelvn.tourbookingsytem.model.TourOperator;
import com.travelvn.tourbookingsytem.model.TourProgram;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TourProgramMapper {
    TourProgram toTourProgram(TourProgramAdRequest tourProgramAdRequest);

    // Phương thức ánh xạ từ Integer sang TourOperator
    default TourOperator map(Integer id) {
        if (id == null) {
            return null;
        }
        TourOperator tourOperator = new TourOperator();
        tourOperator.setId(id); // Gán ID cho đối tượng TourOperator
        return tourOperator;
    }

    TourProgramAdResponse toTourProgramResponse(TourProgram tourProgram);
}
