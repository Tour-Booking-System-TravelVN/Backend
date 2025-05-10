package com.travelvn.tourbookingsytem.controller;

import com.travelvn.tourbookingsytem.dto.request.TourGuideAdRequest;
import com.travelvn.tourbookingsytem.dto.response.TourGuideAdResponse;
import com.travelvn.tourbookingsytem.service.TourGuideAdService;
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
public class TourGuideAdController {

    @Autowired
    private TourGuideAdService tourGuideAdService;

    @GetMapping
    public ResponseEntity<List<TourGuideAdResponse>> getAllTourGuides() {
        return ResponseEntity.ok(tourGuideAdService.getAllTourGuides());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTourGuideById(@PathVariable Integer id) {
        TourGuideAdResponse tourGuide = tourGuideAdService.getTourGuideById(id);
        if (tourGuide == null) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("code", 4040);
            errorResponse.put("message", "Tour guide with ID " + id + " not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
        return ResponseEntity.ok(tourGuide);
    }

    @GetMapping("/search")
    public ResponseEntity<List<TourGuideAdResponse>> searchTourGuides(
            @RequestParam(required = false) String firstname,
            @RequestParam(required = false) String lastname) {
        return ResponseEntity.ok(tourGuideAdService.searchTourGuides(firstname, lastname));
    }

    @PostMapping
    public ResponseEntity<TourGuideAdResponse> createTourGuide(@Valid @RequestBody TourGuideAdRequest tourGuideAdRequest) {
        return ResponseEntity.ok(tourGuideAdService.createTourGuide(tourGuideAdRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTourGuide(@PathVariable Integer id, @Valid @RequestBody TourGuideAdRequest tourGuideAdRequest) {
        TourGuideAdResponse updatedTourGuide = tourGuideAdService.updateTourGuide(id, tourGuideAdRequest);
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
        tourGuideAdService.deleteTourGuide(id);
        return ResponseEntity.noContent().build();
    }
}