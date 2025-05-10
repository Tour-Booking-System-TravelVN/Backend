package com.travelvn.tourbookingsytem.controller;

import com.travelvn.tourbookingsytem.dto.request.TourUnitRequest;
import com.travelvn.tourbookingsytem.dto.response.TourUnitResponse;
import com.travelvn.tourbookingsytem.service.TourUnitService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tour-units")
public class TourUnitController {

    @Autowired
    private TourUnitService tourUnitService;

    @GetMapping
    public ResponseEntity<List<TourUnitResponse>> getAllTourUnits() {
        return ResponseEntity.ok(tourUnitService.getAllTourUnits());
    }

    @GetMapping("/{tourUnitId}")
    public ResponseEntity<TourUnitResponse> getTourUnitById(@PathVariable String tourUnitId) {
        TourUnitResponse tourUnit = tourUnitService.getTourUnitById(tourUnitId);
        return tourUnit != null ? ResponseEntity.ok(tourUnit) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<TourUnitResponse> createTourUnit(@Valid @RequestBody TourUnitRequest tourUnitRequest) {
        return ResponseEntity.ok(tourUnitService.createTourUnit(tourUnitRequest));
    }

    @PutMapping("/{tourUnitId}")
    public ResponseEntity<TourUnitResponse> updateTourUnit(@PathVariable String tourUnitId, @Valid @RequestBody TourUnitRequest tourUnitRequest) {
        TourUnitResponse updatedTourUnit = tourUnitService.updateTourUnit(tourUnitId, tourUnitRequest);
        return updatedTourUnit != null ? ResponseEntity.ok(updatedTourUnit) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{tourUnitId}")
    public ResponseEntity<Void> deleteTourUnit(@PathVariable String tourUnitId) {
        tourUnitService.deleteTourUnit(tourUnitId);
        return ResponseEntity.noContent().build();
    }
}