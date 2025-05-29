package com.travelvn.tourbookingsytem.controller;

import com.travelvn.tourbookingsytem.dto.request.TourRatingRequest;
import com.travelvn.tourbookingsytem.dto.response.ApiResponse;
import com.travelvn.tourbookingsytem.dto.response.TourRatingResponse;
import com.travelvn.tourbookingsytem.service.TourRatingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/rating-tour")
    public ApiResponse<Boolean> createTourRating(@Valid @RequestBody TourRatingRequest tourRatingRequest) {
        return ApiResponse.<Boolean>builder()
                .result(tourRatingService.createTourRating(tourRatingRequest))
                .build();
    }

    @GetMapping("/rating-tour/check")
    public ApiResponse<Boolean> checkTourRating(
            @RequestParam String tourUnitId
    ) {
        return ApiResponse.<Boolean>builder()
                .result(tourRatingService.checkTourRating(tourUnitId))
                .build();
    }
}
