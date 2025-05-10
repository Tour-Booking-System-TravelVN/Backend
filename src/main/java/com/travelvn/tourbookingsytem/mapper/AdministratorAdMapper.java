package com.travelvn.tourbookingsytem.mapper;

import com.travelvn.tourbookingsytem.dto.request.AdministratorAdRequest;
import com.travelvn.tourbookingsytem.dto.response.AdministratorAdResponse;
import com.travelvn.tourbookingsytem.model.Administrator;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AdministratorAdMapper {
    Administrator toAdministrator(AdministratorAdRequest administratorAdRequest);
//    Administrator toAdministrator(AdministratorResponse administratorResponse);

    AdministratorAdResponse toAdministratorResponse(Administrator administrator);
//    AdministratorRequest toAdministratorRequest(Administrator administrator);
}
