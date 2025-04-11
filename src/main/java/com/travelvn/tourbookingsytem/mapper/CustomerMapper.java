package com.travelvn.tourbookingsytem.mapper;

import com.travelvn.tourbookingsytem.dto.request.CustomerRequest;
import com.travelvn.tourbookingsytem.dto.response.CustomerResponse;
import com.travelvn.tourbookingsytem.model.Customer;
import org.mapstruct.*;

@Named("CustomerMapper")
@Mapper(componentModel = "spring", uses = {UserAccountMapper.class}/*, unmappedTargetPolicy = ReportingPolicy.IGNORE*/)
public interface CustomerMapper {
    //Mapping thông tin người đi cùng để đặt tour
    @Mappings({
            @Mapping(source = "userAccount", target = "userAccount", qualifiedByName = {"UserAccountMapper", "toUserAccountEmail"})
    })
    Customer toCustomerToBook(CustomerRequest customerRequest);

//    Customer toCustomer(CustomerResponse customerResponse);

//    CustomerRequest toCustomerRequest(Customer customer);

//    @Named("toCustomerResponse")
//    @Mappings({
//            @Mapping(target = "userAccount", qualifiedByName = {"UserAccountMapper", "toUserAccountResponseWithoutCustomer"})
//    })

    CustomerResponse toCustomerResponse(Customer customer);

//    @Named("toCustomerResponseWithoutUserAccount")
//    @Mappings({
//            @Mapping(target = "userAccount", expression = "java(null)")
//    })
//    CustomerResponse toCustomerResponseWithoutUserAccount(Customer customer);
}
