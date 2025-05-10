package com.travelvn.tourbookingsytem.controller;

import com.travelvn.tourbookingsytem.dto.request.TourUnitAdRequest;
import com.travelvn.tourbookingsytem.dto.response.TourUnitAdResponse;
import com.travelvn.tourbookingsytem.service.TourUnitAdService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tour-units")
public class TourUnitAdController {

    @Autowired
    private TourUnitAdService tourUnitAdService;

    @GetMapping
    public ResponseEntity<List<TourUnitAdResponse>> getAllTourUnits() {
        return ResponseEntity.ok(tourUnitAdService.getAllTourUnits());
    }

    @GetMapping("/{tourUnitId}")
    public ResponseEntity<TourUnitAdResponse> getTourUnitById(@PathVariable String tourUnitId) {
        TourUnitAdResponse tourUnit = tourUnitAdService.getTourUnitById(tourUnitId);
        return tourUnit != null ? ResponseEntity.ok(tourUnit) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<TourUnitAdResponse> createTourUnit(@Valid @RequestBody TourUnitAdRequest tourUnitAdRequest) {
        return ResponseEntity.ok(tourUnitAdService.createTourUnit(tourUnitAdRequest));
    }

    @PutMapping("/{tourUnitId}")
    public ResponseEntity<TourUnitAdResponse> updateTourUnit(@PathVariable String tourUnitId, @Valid @RequestBody TourUnitAdRequest tourUnitAdRequest) {
        TourUnitAdResponse updatedTourUnit = tourUnitAdService.updateTourUnit(tourUnitId, tourUnitAdRequest);
        return updatedTourUnit != null ? ResponseEntity.ok(updatedTourUnit) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{tourUnitId}")
    public ResponseEntity<Void> deleteTourUnit(@PathVariable String tourUnitId) {
        tourUnitAdService.deleteTourUnit(tourUnitId);
        return ResponseEntity.noContent().build();
    }
}