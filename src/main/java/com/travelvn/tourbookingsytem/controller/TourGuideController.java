package com.travelvn.tourbookingsytem.controller;

import com.travelvn.tourbookingsytem.dto.request.TourGuideRequest;
import com.travelvn.tourbookingsytem.dto.response.TourGuideResponse;
import com.travelvn.tourbookingsytem.service.TourGuideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tour-guides")
public class TourGuideController {

    @Autowired
    private TourGuideService tourGuideService;

    @GetMapping
    public ResponseEntity<List<TourGuideResponse>> getAllTourGuides() {
        return ResponseEntity.ok(tourGuideService.getAllTourGuides());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTourGuideById(@PathVariable Integer id) {
        TourGuideResponse tourGuide = tourGuideService.getTourGuideById(id);
        if (tourGuide == null) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("code", 4040);
            errorResponse.put("message", "Tour guide with ID " + id + " not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
        return ResponseEntity.ok(tourGuide);
    }

    @GetMapping("/search")
    public ResponseEntity<List<TourGuideResponse>> searchTourGuides(
            @RequestParam(required = false) String firstname,
            @RequestParam(required = false) String lastname) {
        return ResponseEntity.ok(tourGuideService.searchTourGuides(firstname, lastname));
    }

    @PostMapping
    public ResponseEntity<TourGuideResponse> createTourGuide(@Valid @RequestBody TourGuideRequest tourGuideRequest) {
        return ResponseEntity.ok(tourGuideService.createTourGuide(tourGuideRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTourGuide(@PathVariable Integer id, @Valid @RequestBody TourGuideRequest tourGuideRequest) {
        TourGuideResponse updatedTourGuide = tourGuideService.updateTourGuide(id, tourGuideRequest);
        if (updatedTourGuide == null) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("code", 4040);
            errorResponse.put("message", "Tour guide with ID " + id + " not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
        return ResponseEntity.ok(updatedTourGuide);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTourGuide(@PathVariable Integer id) {
        tourGuideService.deleteTourGuide(id);
        return ResponseEntity.noContent().build();
    }
}