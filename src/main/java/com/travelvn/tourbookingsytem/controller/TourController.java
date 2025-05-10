package com.travelvn.tourbookingsytem.controller;

import com.travelvn.tourbookingsytem.dto.request.TourRequest;
import com.travelvn.tourbookingsytem.dto.response.TourResponse;
import com.travelvn.tourbookingsytem.mapper.TourMapper;
import com.travelvn.tourbookingsytem.model.Tour;
import com.travelvn.tourbookingsytem.service.TourService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tours")
public class TourController {

    private static final Logger logger = LoggerFactory.getLogger(TourController.class);
    @Autowired
    private TourMapper tourMapper;

    @Autowired
    private TourService tourService;

    @GetMapping
    public ResponseEntity<List<TourResponse>> getAllTours() {
        logger.info("Fetching all tours");
        return ResponseEntity.ok(tourService.getAllTours());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TourResponse> getTourById(@PathVariable String id) {
        logger.info("Fetching tour with ID: {}", id);
        try {
            TourResponse tour = tourService.getTourById(id);
            return ResponseEntity.ok(tour);
        } catch (RuntimeException e) {
            logger.warn("Tour with ID {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<TourResponse> createTour(@Valid @RequestBody TourRequest tourRequest) {
        logger.info("Creating tour from request: {}", tourRequest);
        TourResponse response = tourService.createTour(tourRequest);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TourResponse> updateTour(@PathVariable String id, @Valid @RequestBody TourRequest tourRequest) {
        logger.info("Updating tour with ID: {}", id);
        try {
            TourResponse updatedTour = tourService.updateTour(id, tourRequest);
            return ResponseEntity.ok(updatedTour);
        } catch (RuntimeException e) {
            logger.warn("Tour with ID {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchTours(@RequestParam String keyword) {
        logger.info("Searching tours with keyword: {}", keyword);


        Optional<Tour> tourById = tourService.findById(keyword);
        if (tourById.isPresent()) {
            TourResponse response = tourMapper.toResponse(tourById.get());
            return ResponseEntity.ok(Collections.singletonList(response)); // Trả về list 1 phần tử để frontend dùng chung
        }


        List<Tour> tourList = tourService.searchByNameOrLocation(keyword);
        List<TourResponse> responseList = tourList.stream()
                .map(tourMapper::toResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responseList);
    }




    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTour(@PathVariable String id) {
        logger.info("Deleting tour with ID: {}", id);
        try {
            tourService.deleteTour(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            logger.warn("Tour with ID {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }
}