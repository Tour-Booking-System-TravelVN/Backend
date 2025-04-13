package com.travelvn.tourbookingsytem.mapper;

import com.travelvn.tourbookingsytem.dto.request.CustomerRequest;
import com.travelvn.tourbookingsytem.dto.response.CustomerResponse;
import com.travelvn.tourbookingsytem.model.Customer;
import org.mapstruct.*;

@Named("CustomerMapper")
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {UserAccountMapper.class}/*, unmappedTargetPolicy = ReportingPolicy.IGNORE*/)
public interface CustomerMapper {
    //Mapping thông tin người đi cùng để đặt tour
//    {"UserAccountMapper", "toUserAccountEmail"}
//    @Mappings({
//            @Mapping(source = "userAccount", target = "userAccount", qualifiedByName = "toUserAccountEmail")
//    })
    Customer toCustomerToBook(CustomerRequest customerRequest);

    //Mapping thông tin khách hàng để đăng ký
//    @Named("toCustomerToRegister")
//    @Mappings({
//            @Mapping(target = "userAccount", ignore = true)
//    })
//    Customer toCustomerToRegister(CustomerRequest customerRequest);

//    Customer toCustomer(CustomerResponse customerResponse);

//    CustomerRequest toCustomerRequest(Customer customer);

//    @Named("toCustomerResponse")
//    @Mappings({
//            @Mapping(target = "userAccount", qualifiedByName = {"UserAccountMapper", "toUserAccountResponseWithoutCustomer"})
//    })

    //Mapping lấy thông tin người dùng
//    @Named("toCustomerResponseWithoutUserAccount")
//    @Mappings({
//            @Mapping(target = "userAccount", ignore = true),
//    })
//    CustomerResponse toCustomerResponseWithoutUserAccount(Customer customer);

//    @Named("toCustomerResponseWithoutUserAccount")
//    @Mappings({
//            @Mapping(target = "userAccount", ignore = true)
//    })
    CustomerResponse toCustomerResponse(Customer customer);
}
