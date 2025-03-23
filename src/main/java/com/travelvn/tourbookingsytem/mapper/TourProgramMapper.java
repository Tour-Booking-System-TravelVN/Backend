package com.travelvn.tourbookingsytem.mapper;

import com.travelvn.tourbookingsytem.dto.request.CustomerRequest;
import com.travelvn.tourbookingsytem.dto.request.TourProgramRequest;
import com.travelvn.tourbookingsytem.dto.response.TourProgramResponse;
import com.travelvn.tourbookingsytem.model.Customer;
import com.travelvn.tourbookingsytem.model.TourProgram;
import org.mapstruct.Mapper;

//@Component
@Mapper(componentModel = "spring")
public interface TourProgramMapper {
    TourProgram toTourProgram(TourProgramRequest tourProgramRequest);
//    TourProgram toTourProgram(TourProgramResponse tourProgramResponse);

    TourProgramResponse toTourProgramResponse(TourProgram tourProgram);
//    TourProgramRequest toTourProgramRequest(TourProgram tourProgram);
}
