package com.travelvn.tourbookingsytem.mapper;

import com.travelvn.tourbookingsytem.dto.request.CustomerRequest;
import com.travelvn.tourbookingsytem.dto.request.TourUnitRequest;
import com.travelvn.tourbookingsytem.dto.response.TourUnitResponse;
import com.travelvn.tourbookingsytem.model.Customer;
import com.travelvn.tourbookingsytem.model.TourUnit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

//@Component
@Named("TourUnitMapper")
@Mapper(componentModel = "spring", uses = {TourMapper.class})
public interface TourUnitMapper {
    TourUnit toTourUnit(TourUnitRequest tourUnitRequest);
//    TourUnitRequest toTourUnitRequest(TourUnit tourUnit);

//    TourUnit toTourUnit(TourUnitResponse tourUnitResponse);
//    TourUnitResponse toTourUnitResponse(TourUnit tourUnit);

    @Named("toTourUnitDTOFound")
    @Mappings({
            @Mapping(target = "tourOperator", ignore = true),
            @Mapping(target = "lastUpdatedOperator", ignore = true),
            @Mapping(target = "createdTime", ignore = true),
            @Mapping(target = "lastUpdatedTime", ignore = true),
            @Mapping(source = "tour", target = "tour", qualifiedByName = {"TourMapper", "toTourResponseByFound"})
    })
    TourUnitResponse toTourUnitResponseByFound(TourUnit tourUnit);
}
