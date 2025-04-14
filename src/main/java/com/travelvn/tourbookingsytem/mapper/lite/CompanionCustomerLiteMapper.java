package com.travelvn.tourbookingsytem.mapper.lite;

import com.travelvn.tourbookingsytem.dto.response.lite.CompanionCustomerResponseLite;
import com.travelvn.tourbookingsytem.model.CompanionCustomer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface CompanionCustomerLiteMapper {
    @Mappings({
            @Mapping(target = "fullName", expression = "java(companionCustomer.getC().getFirstname() + \" \" + companionCustomer.getC().getLastname())")
    })
    CompanionCustomerResponseLite toCompanionCustomerResponseLite(CompanionCustomer companionCustomer);
}
