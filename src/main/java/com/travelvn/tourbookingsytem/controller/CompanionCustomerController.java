package com.travelvn.tourbookingsytem.controller;

import com.travelvn.tourbookingsytem.dto.response.ApiResponse;
import com.travelvn.tourbookingsytem.dto.response.lite.CompanionCustomerResponseLite;
import com.travelvn.tourbookingsytem.service.CompanionCustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/companion-customer")
public class CompanionCustomerController {
    private final CompanionCustomerService companionCustomerService;

//    @GetMapping("/tour-detail/{tourunitid}")
//    public ApiResponse<List<CompanionCustomerResponseLite>> getCompanionCustomerWithMeByTourUnitId(@PathVariable("tourunitid") String tourunitid) {
//        return ApiResponse.<List<CompanionCustomerResponseLite>>builder()
//                .result(companionCustomerService.getCompanionCustomerWithMeByTourUnitId(tourunitid))
//                .build();
//    }
}
