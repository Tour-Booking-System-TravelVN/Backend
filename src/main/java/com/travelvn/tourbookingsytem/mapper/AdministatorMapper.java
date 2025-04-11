package com.travelvn.tourbookingsytem.mapper;

import com.travelvn.tourbookingsytem.dto.request.AdministratorRequest;
import com.travelvn.tourbookingsytem.dto.response.AdministratorResponse;
import com.travelvn.tourbookingsytem.model.Administrator;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring"/*, uses = {UserAccountMapper.class, TourRatingMapper.class}*/)
public interface AdministatorMapper {
    Administrator toAdministrator(AdministratorRequest administratorRequest);
//    Administrator toAdministrator(AdministratorResponse administratorResponse);

    AdministratorResponse toAdministratorResponse(Administrator administrator);
//    AdministratorRequest toAdministratorRequest(Administrator administrator);
}
