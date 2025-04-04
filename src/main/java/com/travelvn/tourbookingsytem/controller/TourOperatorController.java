package com.travelvn.tourbookingsytem.controller;

import com.travelvn.tourbookingsytem.model.TourOperator;
import com.travelvn.tourbookingsytem.service.TourOperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tour-operators")
public class TourOperatorController {
    @Autowired
    private TourOperatorService tourOperatorService;

    @GetMapping
    public ResponseEntity<List<TourOperator>> getAllTourOperators() {
        return ResponseEntity.ok(tourOperatorService.getAllTourOperators());
    }

    @PostMapping
    public ResponseEntity<TourOperator> createTourOperator(@RequestBody TourOperator tourOperator) {
        return ResponseEntity.ok(tourOperatorService.createTourOperator(tourOperator));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TourOperator> updateTourOperator(@PathVariable Integer id, @RequestBody TourOperator tourOperator) {
        return ResponseEntity.ok(tourOperatorService.updateTourOperator(id, tourOperator));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTourOperator(@PathVariable Integer id) {
        tourOperatorService.deleteTourOperator(id);
        return ResponseEntity.ok().build();
    }
}