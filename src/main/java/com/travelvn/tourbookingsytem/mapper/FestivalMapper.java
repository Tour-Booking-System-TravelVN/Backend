package com.travelvn.tourbookingsytem.mapper;

import com.travelvn.tourbookingsytem.dto.request.CustomerRequest;
import com.travelvn.tourbookingsytem.dto.request.FestivalRequest;
import com.travelvn.tourbookingsytem.dto.response.FestivalResponse;
import com.travelvn.tourbookingsytem.model.Customer;
import com.travelvn.tourbookingsytem.model.Festival;
import org.mapstruct.Mapper;

//@Component
@Mapper(componentModel = "spring")
public interface FestivalMapper {
    Festival toFestival(FestivalRequest festivalRequest);
//    Festival toFestival(FestivalResponse festivalResponse);

//    FestivalRequest toFestivalRequest(Festival festival);
    FestivalResponse toFestivalResponse(Festival festival);
}
