package com.travelvn.tourbookingsytem.mapper;

import com.travelvn.tourbookingsytem.dto.request.CustomerRequest;
import com.travelvn.tourbookingsytem.dto.response.CustomerResponse;
import com.travelvn.tourbookingsytem.model.Customer;
import org.mapstruct.*;

@Named("CustomerMapper")
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {UserAccountMapper.class})
public interface CustomerMapper {
//    Customer toCustomer(CustomerRequest customerRequest);
//    Customer toCustomer(CustomerResponse customerResponse);

    CustomerRequest toCustomerRequest(Customer customer);

    @Named("toCustomerResponse")
    @Mappings({
            @Mapping(target = "userAccount", qualifiedByName = {"UserAccountMapper", "toUserAccountResponseWithoutCustomer"})
    })
    CustomerResponse toCustomerResponse(Customer customer);

//    @Named("toCustomerResponseWithoutUserAccount")
//    @Mappings({
//            @Mapping(target = "userAccount", expression = "java(null)")
//    })
//    CustomerResponse toCustomerResponseWithoutUserAccount(Customer customer);
}
