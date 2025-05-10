package com.travelvn.tourbookingsytem.controller;

import com.travelvn.tourbookingsytem.dto.request.TourOperatorRequest;
import com.travelvn.tourbookingsytem.dto.response.TourOperatorResponse;
import com.travelvn.tourbookingsytem.service.TourOperatorService;
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
public class TourOperatorController {

    @Autowired
    private TourOperatorService tourOperatorService;

    @GetMapping
    public ResponseEntity<List<TourOperatorResponse>> getAllTourOperators() {
        return ResponseEntity.ok(tourOperatorService.getAllTourOperators());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTourOperatorById(@PathVariable int id) {
        TourOperatorResponse tourOperator = tourOperatorService.findTourOperatorById(id);
        if (tourOperator == null) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("code", 4040);
            errorResponse.put("message", "Tour operator with ID " + id + " not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
        return ResponseEntity.ok(tourOperator);
    }

    @PostMapping
    public ResponseEntity<TourOperatorResponse> createTourOperator(@Valid @RequestBody TourOperatorRequest tourOperatorRequest) {
        return ResponseEntity.ok(tourOperatorService.createTourOperator(tourOperatorRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTourOperator(@PathVariable Integer id, @Valid @RequestBody TourOperatorRequest tourOperatorRequest) {
        TourOperatorResponse updatedTourOperator = tourOperatorService.updateTourOperator(id, tourOperatorRequest);
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
        tourOperatorService.deleteTourOperator(id);
        return ResponseEntity.noContent().build();
    }
}