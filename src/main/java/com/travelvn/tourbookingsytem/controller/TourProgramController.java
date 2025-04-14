package com.travelvn.tourbookingsytem.controller;

import com.travelvn.tourbookingsytem.dto.response.ApiResponse;
import com.travelvn.tourbookingsytem.dto.response.TourProgramResponse;
import com.travelvn.tourbookingsytem.service.TourProgramService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/program")
public class TourProgramController {

    private final TourProgramService tourProgramService;

    @GetMapping("/tour-detail/{tourid}")
    public ApiResponse<List<TourProgramResponse>> getTourProgramByTour(@PathVariable("tourid") String tourid) {
        return ApiResponse.<List<TourProgramResponse>>builder()
                .result(tourProgramService.getTourProgramByTour(tourid))
                .build();
    }
}
