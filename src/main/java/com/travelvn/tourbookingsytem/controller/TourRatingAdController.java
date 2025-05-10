package com.travelvn.tourbookingsytem.controller;

import com.travelvn.tourbookingsytem.dto.request.TourRatingStatusAdUpdate;
import com.travelvn.tourbookingsytem.dto.response.TourRatingAdResponse;
import com.travelvn.tourbookingsytem.service.TourRatingAdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/tour-ratings")
public class TourRatingAdController {

    private static final Logger logger = LoggerFactory.getLogger(TourRatingAdController.class);

    @Autowired
    private TourRatingAdService tourRatingAdService;

    @GetMapping
    public ResponseEntity<List<TourRatingAdResponse>> getAllTourRatings() {
        logger.info("Fetching all tour ratings");
        return ResponseEntity.ok(tourRatingAdService.getAllTourRatings());
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateTourRatingStatus(@PathVariable Integer id, @Valid @RequestBody TourRatingStatusAdUpdate statusUpdate) {
        logger.info("Updating tour rating status for ID: {} to status: {}", id, statusUpdate.getStatus());
        try {
            TourRatingAdResponse updatedRating = tourRatingAdService.updateTourRatingStatus(id, statusUpdate.getStatus());
            return ResponseEntity.ok(updatedRating);
        } catch (IllegalArgumentException e) {
            logger.warn("Failed to update tour rating ID {}: {}", id, e.getMessage());
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("code", 4000);
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (RuntimeException e) {
            logger.warn("Failed to update tour rating ID {}: {}", id, e.getMessage());
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("code", 4040);
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }
}