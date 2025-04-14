package com.travelvn.tourbookingsytem.controller;

import com.travelvn.tourbookingsytem.dto.request.CustomerRequest;
import com.travelvn.tourbookingsytem.dto.request.customer.UpdateCustomerRequest;
import com.travelvn.tourbookingsytem.dto.response.ApiResponse;
import com.travelvn.tourbookingsytem.dto.response.CustomerResponse;
import com.travelvn.tourbookingsytem.dto.response.UserAccountResponse;
import com.travelvn.tourbookingsytem.model.Customer;
import com.travelvn.tourbookingsytem.service.CustomerService;
import com.travelvn.tourbookingsytem.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;
    private final UserAccountService userAccountService;

    /**
     * Api lấy thông tin của mình
     * @return thông tin của mình
     */
    @GetMapping("/myinfo")
    public ApiResponse<UserAccountResponse> getMyInfo(){
        log.info("CONTROLLER MYINFO");
        return ApiResponse.<UserAccountResponse>builder()
                .result(userAccountService.getMyInfo())
                .build();
    }

    @PutMapping("/myinfo/update")
    public ApiResponse<UserAccountResponse> updateMyInfo(@RequestBody UpdateCustomerRequest updateCustomerRequest){
        return ApiResponse.<UserAccountResponse>builder()
                .result(userAccountService.updateMyInfo(updateCustomerRequest))
                .build();
    }


}
