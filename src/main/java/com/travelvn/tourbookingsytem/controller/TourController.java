package com.travelvn.tourbookingsytem.controller;


import com.travelvn.tourbookingsytem.model.Tour;
import com.travelvn.tourbookingsytem.service.TourService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tours")
public class TourController {

    @Autowired
    private TourService tourService;

    @GetMapping
    public ResponseEntity<List<Tour>> getAllTours() {
        return ResponseEntity.ok(tourService.getAllTours());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tour> getTourById(@PathVariable Integer id) {
        Tour tour = tourService.getTourById(id);
        if (tour != null) {
            return ResponseEntity.ok(tour);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Tour> createTour(@Valid @RequestBody Tour tour) {
        return ResponseEntity.ok(tourService.createTour(tour));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tour> updateTour(@PathVariable Integer id, @Valid @RequestBody Tour tourDetails) {
        Tour updatedTour = tourService.updateTour(id, tourDetails);
        if (updatedTour != null) {
            return ResponseEntity.ok(updatedTour);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTour(@PathVariable Integer id) {
        tourService.deleteTour(id);
        return ResponseEntity.noContent().build();
    }
}