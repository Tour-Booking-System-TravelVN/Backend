package com.travelvn.tourbookingsytem.controller;

import com.travelvn.tourbookingsytem.dto.request.FindTourRequest;
import com.travelvn.tourbookingsytem.dto.response.ApiResponse;
import com.travelvn.tourbookingsytem.dto.response.TourResponse;
import com.travelvn.tourbookingsytem.dto.response.TourUnitResponse;
import com.travelvn.tourbookingsytem.service.TourService;
import com.travelvn.tourbookingsytem.service.TourUnitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/tourunit")
public class TourUnitController {
    private final TourUnitService tourUnitService;

//    @PermitAll
    //DEPRECATED
    @PostMapping("/foundtourlist")
    public ApiResponse<List<TourUnitResponse>> findTours(@RequestBody FindTourRequest findTourRequest) {
        return tourUnitService.findTours(findTourRequest);
    }

    @GetMapping("/foundtourlist")
    public ApiResponse<List<TourUnitResponse>> findToursList(@ModelAttribute FindTourRequest findTourRequest) {
        return tourUnitService.findTours(findTourRequest);
    }

    @GetMapping("/mytours")
    public ApiResponse<Page<TourUnitResponse>> myTours(@RequestParam Map<String, String> params) {
        String status = params.get("status");
        int page = Integer.parseInt(params.get("page"));
        return ApiResponse.<Page<TourUnitResponse>>builder()
                .result(tourUnitService.getMyTours(status, page))
                .build();
    }
}
