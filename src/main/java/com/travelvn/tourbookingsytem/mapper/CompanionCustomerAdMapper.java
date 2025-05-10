package com.travelvn.tourbookingsytem.mapper;

import com.travelvn.tourbookingsytem.dto.request.CompanionCustomerAdRequest;
import com.travelvn.tourbookingsytem.dto.response.CompanionCustomerAdResponse;
import com.travelvn.tourbookingsytem.model.CompanionCustomer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {BookingMapper.class, CustomerAdMapper.class})
public interface CompanionCustomerAdMapper {
    CompanionCustomer toCompanionCustomer(CompanionCustomerAdRequest companionCustomerAdRequest);
//    CompanionCustomer toCompanionCustomer(CompanionCustomerResponse companionCustomerResponse);
default byte map(Boolean value) {
    return value != null && value ? (byte) 1 : (byte) 0;
}
//    CompanionCustomerRequest toCompanionCustomerRequest(CompanionCustomer companionCustomer);
    CompanionCustomerAdResponse toCompanionCustomerResponse(CompanionCustomer companionCustomer);
}
