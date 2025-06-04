package com.travelvn.tourbookingsytem.controller;

import com.travelvn.tourbookingsytem.dto.response.ApiResponse;
import com.travelvn.tourbookingsytem.dto.response.UserAccountResponse;
import com.travelvn.tourbookingsytem.service.CustomerService;
import com.travelvn.tourbookingsytem.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final CustomerService customerService;
    private final UserAccountService userAccountService;

    /**
     * Api lấy thông tin của mình
     *
     * @return thông tin của mình
     */
    @GetMapping("/myinfo")
    public ApiResponse<UserAccountResponse> getMyInfo() {
        log.info("CONTROLLER MYINFO");
        return ApiResponse.<UserAccountResponse>builder()
                .result(userAccountService.getMyInfo())
                .build();
    }

}
