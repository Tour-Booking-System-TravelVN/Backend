package com.travelvn.tourbookingsytem.controller;

import com.travelvn.tourbookingsytem.dto.request.TourOperatorAdRequest;
import com.travelvn.tourbookingsytem.dto.response.TourOperatorAdResponse;
import com.travelvn.tourbookingsytem.service.TourOperatorAdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tour-operators")
public class TourOperatorAdController {

    @Autowired
    private TourOperatorAdService tourOperatorAdService;

    @GetMapping
    public ResponseEntity<List<TourOperatorAdResponse>> getAllTourOperators() {
        return ResponseEntity.ok(tourOperatorAdService.getAllTourOperators());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTourOperatorById(@PathVariable int id) {
        TourOperatorAdResponse tourOperator = tourOperatorAdService.findTourOperatorById(id);
        if (tourOperator == null) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("code", 4040);
            errorResponse.put("message", "Tour operator with ID " + id + " not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
        return ResponseEntity.ok(tourOperator);
    }

    @PostMapping
    public ResponseEntity<TourOperatorAdResponse> createTourOperator(@Valid @RequestBody TourOperatorAdRequest tourOperatorAdRequest) {
        return ResponseEntity.ok(tourOperatorAdService.createTourOperator(tourOperatorAdRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTourOperator(@PathVariable Integer id, @Valid @RequestBody TourOperatorAdRequest tourOperatorAdRequest) {
        TourOperatorAdResponse updatedTourOperator = tourOperatorAdService.updateTourOperator(id, tourOperatorAdRequest);
        if (updatedTourOperator == null) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("code", 4040);
            errorResponse.put("message", "Tour operator with ID " + id + " not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
        return ResponseEntity.ok(updatedTourOperator);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTourOperator(@PathVariable Integer id) {
        tourOperatorAdService.deleteTourOperator(id);
        return ResponseEntity.noContent().build();
    }
}