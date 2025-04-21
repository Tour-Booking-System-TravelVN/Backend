package com.travelvn.tourbookingsytem.mapper;

import com.travelvn.tourbookingsytem.dto.request.CompanionCustomerRequest;
import com.travelvn.tourbookingsytem.dto.response.CompanionCustomerResponse;
import com.travelvn.tourbookingsytem.model.CompanionCustomer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {BookingMapper.class, CustomerMapper.class})
public interface CompanionCustomerMapper {
    CompanionCustomer toCompanionCustomer(CompanionCustomerRequest companionCustomerRequest);
//    CompanionCustomer toCompanionCustomer(CompanionCustomerResponse companionCustomerResponse);
default byte map(Boolean value) {
    return value != null && value ? (byte) 1 : (byte) 0;
}
//    CompanionCustomerRequest toCompanionCustomerRequest(CompanionCustomer companionCustomer);
    CompanionCustomerResponse toCompanionCustomerResponse(CompanionCustomer companionCustomer);
}
