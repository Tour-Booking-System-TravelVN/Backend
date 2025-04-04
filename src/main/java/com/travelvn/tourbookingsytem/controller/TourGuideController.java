package com.travelvn.tourbookingsytem.controller;


import com.travelvn.tourbookingsytem.model.TourGuide;
import com.travelvn.tourbookingsytem.service.TourGuideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tour-guides")
public class TourGuideController {

    @Autowired
    private TourGuideService tourGuideService;

    @GetMapping
    public ResponseEntity<List<TourGuide>> getAllTourGuides() {
        return ResponseEntity.ok(tourGuideService.getAllTourGuides());
    }

    @GetMapping("/search")
    public ResponseEntity<List<TourGuide>> searchTourGuides(
            @RequestParam String firstname,
            @RequestParam String lastname) {
        return ResponseEntity.ok(tourGuideService.searchTourGuides(firstname, lastname));
    }
}