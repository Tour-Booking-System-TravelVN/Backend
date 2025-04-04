package com.travelvn.tourbookingsytem.controller;

import com.travelvn.tourbookingsytem.model.TourRating;
import com.travelvn.tourbookingsytem.service.TourRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tour-ratings")
public class TourRatingController {
    @Autowired
    private TourRatingService tourRatingService;

    @GetMapping
    public ResponseEntity<List<TourRating>> getAllRatings() {
        return ResponseEntity.ok(tourRatingService.getAllRatings());
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<TourRating> updateRatingStatus(@PathVariable Integer id, @RequestParam String status) {
        return ResponseEntity.ok(tourRatingService.updateRatingStatus(id, status));
    }
}