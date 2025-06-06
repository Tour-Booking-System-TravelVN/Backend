package com.travelvn.tourbookingsytem.controller;

import com.travelvn.tourbookingsytem.dto.request.FindTourRequest;
import com.travelvn.tourbookingsytem.dto.response.ApiResponse;
import com.travelvn.tourbookingsytem.dto.response.TourResponse;
import com.travelvn.tourbookingsytem.dto.response.TourUnitResponse;
import com.travelvn.tourbookingsytem.dto.response.peace.TourCalendarResponse;
import com.travelvn.tourbookingsytem.dto.response.peace.TourUnitCalendarResponse;
import com.travelvn.tourbookingsytem.service.TourService;
import com.travelvn.tourbookingsytem.service.TourUnitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
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

    @GetMapping("/calendar")
    public ApiResponse<List<TourUnitCalendarResponse>> getTourUnitCalendar(@RequestParam Map<String, String> params) {
//        log.info("CHECK TU CALENDAR");
        int month = Integer.parseInt(params.get("month"));
        int year = Integer.parseInt(params.get("year"));
        String tourId = params.get("tourid");

        return ApiResponse.<List<TourUnitCalendarResponse>>builder()
                .result(tourUnitService.getTourUnitCalendar(month, year, tourId))
                .build();
    }

    @GetMapping("/detail")
    public ApiResponse<TourUnitResponse> getTourCalendar(@RequestParam("tourUnitId") String tourUnitId) {
        return ApiResponse.<TourUnitResponse>builder()
                .result(tourUnitService.getTourUnit(tourUnitId))
                .build();
    }

//    @GetMapping("/mytours")
//    public ApiResponse<Page<TourUnitResponse>> myTours(@RequestParam Map<String, String> params) {
//        String status = params.get("status");
//        int page = Integer.parseInt(params.get("page"));
//        return ApiResponse.<Page<TourUnitResponse>>builder()
//                .result(tourUnitService.getMyTours(status, page))
//                .build();
//    }
}
