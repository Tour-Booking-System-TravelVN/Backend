package com.travelvn.tourbookingsytem.controller;

import com.travelvn.tourbookingsytem.dto.response.ApiResponse;
import com.travelvn.tourbookingsytem.dto.response.FestivalResponse;
import com.travelvn.tourbookingsytem.service.FestivalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/festival")
@RequiredArgsConstructor
public class FestivalController {

    private final FestivalService festivalService;

    @GetMapping("/carousel")
    public ApiResponse<List<FestivalResponse>> getFestivalsCarousel() {
        return ApiResponse.<List<FestivalResponse>>builder()
                .result(festivalService.getFestivalsCarousel())
                .build();
    }
}
