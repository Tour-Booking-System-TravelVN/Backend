package com.travelvn.tourbookingsytem.mapper;

import com.travelvn.tourbookingsytem.dto.request.CompanionCustomerAdRequest;
import com.travelvn.tourbookingsytem.dto.response.CompanionCustomerAdResponse;
import com.travelvn.tourbookingsytem.model.CompanionCustomer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {BookingMapper.class, CustomerAdMapper.class})
public interface CompanionCustomerAdMapper {
    @Mapping(target = "booking", source = "booking", qualifiedByName = "toEntity")
    CompanionCustomer toCompanionCustomer(CompanionCustomerAdRequest companionCustomerAdRequest);

    @Mapping(target = "booking", source = "booking", qualifiedByName = "toResponse")
    CompanionCustomerAdResponse toCompanionCustomerResponse(CompanionCustomer companionCustomer);
}
