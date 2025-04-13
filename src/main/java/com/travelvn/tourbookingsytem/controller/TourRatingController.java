package com.travelvn.tourbookingsytem.controller;

import com.travelvn.tourbookingsytem.dto.response.ApiResponse;
import com.travelvn.tourbookingsytem.dto.response.TourRatingResponse;
import com.travelvn.tourbookingsytem.service.TourRatingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/rating")
@RequiredArgsConstructor
public class TourRatingController {

    private final TourRatingService tourRatingService;

    @GetMapping("/tour-detail/{tourid}")
    public ApiResponse<List<TourRatingResponse>> getTourRatingsByTour(@PathVariable("tourid") String tourid) {
        return ApiResponse.<List<TourRatingResponse>>builder()
                .result(tourRatingService.getTourRatingByTour(tourid))
                .build();
    }
}
