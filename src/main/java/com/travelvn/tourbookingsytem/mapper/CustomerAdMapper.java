package com.travelvn.tourbookingsytem.mapper;

import com.travelvn.tourbookingsytem.dto.request.CustomerAdRequest;
import com.travelvn.tourbookingsytem.dto.response.CustomerAdResponse;
import com.travelvn.tourbookingsytem.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CustomerAdMapper {
    CustomerAdMapper INSTANCE = Mappers.getMapper(CustomerAdMapper.class);

    @Mapping(source = "dateOfBirth", target = "dateOfBirth")
    Customer toEntity(CustomerAdRequest customerAdRequest);

    CustomerAdResponse toResponse(Customer customer);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "dateOfBirth", target = "dateOfBirth")
    void updateEntityFromRequest(CustomerAdRequest customerAdRequest, @MappingTarget Customer customer);
}