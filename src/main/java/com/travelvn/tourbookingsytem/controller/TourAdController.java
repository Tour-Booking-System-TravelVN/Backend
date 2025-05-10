package com.travelvn.tourbookingsytem.controller;

import com.travelvn.tourbookingsytem.dto.request.TourAdRequest;
import com.travelvn.tourbookingsytem.dto.response.TourAdResponse;
import com.travelvn.tourbookingsytem.mapper.TourMapper;
import com.travelvn.tourbookingsytem.model.Tour;
import com.travelvn.tourbookingsytem.service.TourAdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tours")
public class TourAdController {

    private static final Logger logger = LoggerFactory.getLogger(TourAdController.class);
    @Autowired
    private TourMapper tourMapper;

    @Autowired
    private TourAdService tourAdService;

    @GetMapping
    public ResponseEntity<List<TourAdResponse>> getAllTours() {
        logger.info("Fetching all tours");
        return ResponseEntity.ok(tourAdService.getAllTours());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TourAdResponse> getTourById(@PathVariable String id) {
        logger.info("Fetching tour with ID: {}", id);
        try {
            TourAdResponse tour = tourAdService.getTourById(id);
            return ResponseEntity.ok(tour);
        } catch (RuntimeException e) {
            logger.warn("Tour with ID {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<TourAdResponse> createTour(@Valid @RequestBody TourAdRequest tourAdRequest) {
        logger.info("Creating tour from request: {}", tourAdRequest);
        TourAdResponse response = tourAdService.createTour(tourAdRequest);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TourAdResponse> updateTour(@PathVariable String id, @Valid @RequestBody TourAdRequest tourAdRequest) {
        logger.info("Updating tour with ID: {}", id);
        try {
            TourAdResponse updatedTour = tourAdService.updateTour(id, tourAdRequest);
            return ResponseEntity.ok(updatedTour);
        } catch (RuntimeException e) {
            logger.warn("Tour with ID {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchTours(@RequestParam String keyword) {
        logger.info("Searching tours with keyword: {}", keyword);


        Optional<Tour> tourById = tourAdService.findById(keyword);
        if (tourById.isPresent()) {
            TourAdResponse response = tourMapper.toResponse(tourById.get());
            return ResponseEntity.ok(Collections.singletonList(response)); // Trả về list 1 phần tử để frontend dùng chung
        }


        List<Tour> tourList = tourAdService.searchByNameOrLocation(keyword);
        List<TourAdResponse> responseList = tourList.stream()
                .map(tourMapper::toResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responseList);
    }




    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTour(@PathVariable String id) {
        logger.info("Deleting tour with ID: {}", id);
        try {
            tourAdService.deleteTour(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            logger.warn("Tour with ID {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }
}