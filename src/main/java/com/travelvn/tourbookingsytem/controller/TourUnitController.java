package com.travelvn.tourbookingsytem.controller;

import com.travelvn.tourbookingsytem.model.TourUnit;
import com.travelvn.tourbookingsytem.service.TourUnitService;
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
    public ResponseEntity<List<TourUnit>> getAllTourUnits() {
        return ResponseEntity.ok(tourUnitService.getAllTourUnits());
    }

    @GetMapping("/{tourUnitId}")
    public ResponseEntity<TourUnit> getTourUnitById(@PathVariable String tourUnitId) {
        return tourUnitService.getTourUnitById(tourUnitId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TourUnit> createTourUnit(@RequestBody TourUnit tourUnit) {
        return ResponseEntity.ok(tourUnitService.createTourUnit(tourUnit));
    }

    @PutMapping("/{tourUnitId}")
    public ResponseEntity<TourUnit> updateTourUnit(@PathVariable String tourUnitId, @RequestBody TourUnit tourUnit) {
        return tourUnitService.updateTourUnit(tourUnitId, tourUnit)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{tourUnitId}")
    public ResponseEntity<Void> deleteTourUnit(@PathVariable String tourUnitId) {
        if (tourUnitService.deleteTourUnit(tourUnitId)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}