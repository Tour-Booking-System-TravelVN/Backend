package com.travelvn.tourbookingsytem.controller;

import com.travelvn.tourbookingsytem.dto.request.FindTourRequest;
import com.travelvn.tourbookingsytem.dto.response.ApiResponse;
import com.travelvn.tourbookingsytem.dto.response.TourResponse;
import com.travelvn.tourbookingsytem.service.TourService;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/tour")
public class TourController {
    private final TourService tourService;

//    @PermitAll
    @PostMapping("/foundtourlist")
    public ApiResponse<List<TourResponse>> findTours(@RequestBody FindTourRequest findTourRequest) {
        log.info("findTours method called");
        return ApiResponse.<List<TourResponse>>builder()
                .result(tourService.findTours(findTourRequest))
                .build();
    }
}
