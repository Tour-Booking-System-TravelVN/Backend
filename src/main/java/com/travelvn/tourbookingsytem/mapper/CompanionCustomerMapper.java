package com.travelvn.tourbookingsytem.mapper;

import com.travelvn.tourbookingsytem.dto.request.CompanionCustomerRequest;
import com.travelvn.tourbookingsytem.dto.response.CompanionCustomerResponse;
import com.travelvn.tourbookingsytem.model.CompanionCustomer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {BookingMapper.class, CustomerMapper.class})
public interface CompanionCustomerMapper {
    CompanionCustomer toCompanionCustomer(CompanionCustomerRequest companionCustomerRequest);
//    CompanionCustomer toCompanionCustomer(CompanionCustomerResponse companionCustomerResponse);

//    CompanionCustomerRequest toCompanionCustomerRequest(CompanionCustomer companionCustomer);
    CompanionCustomerResponse toCompanionCustomerResponse(CompanionCustomer companionCustomer);
}
