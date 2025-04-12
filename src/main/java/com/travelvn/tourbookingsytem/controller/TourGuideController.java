package com.travelvn.tourbookingsytem.controller;

import com.travelvn.tourbookingsytem.model.TourGuide;
import com.travelvn.tourbookingsytem.service.TourGuideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
//test toan bo xong
@RestController
@RequestMapping("/api/tour-guides")
public class TourGuideController {

    @Autowired
    private TourGuideService tourGuideService;

    // Lấy danh sách tất cả tour guide
    @GetMapping
    public ResponseEntity<List<TourGuide>> getAllTourGuides() {
        return ResponseEntity.ok(tourGuideService.getAllTourGuides());
    }

    // Lấy tour guide theo ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getTourGuideById(@PathVariable Integer id) {
        TourGuide tourGuide = tourGuideService.getTourGuideById(id);
        if (tourGuide == null) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("code", 4040);
            errorResponse.put("message", "Tour guide with ID " + id + " not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
        return ResponseEntity.ok(tourGuide);
    }

    // Tìm kiếm tour guide theo firstname và lastname
    @GetMapping("/search")
    public ResponseEntity<List<TourGuide>> searchTourGuides(
            @RequestParam(required = false) String firstname,
            @RequestParam(required = false) String lastname) {
        return ResponseEntity.ok(tourGuideService.searchTourGuides(firstname, lastname));
    }

    // Thêm tour guide mới
    @PostMapping
    public ResponseEntity<TourGuide> createTourGuide(@RequestBody TourGuide tourGuide) {
        return ResponseEntity.ok(tourGuideService.createTourGuide(tourGuide));
    }

    // Cập nhật tour guide
    @PutMapping("/{id}")
    public ResponseEntity<?> updateTourGuide(@PathVariable Integer id, @RequestBody TourGuide tourGuideDetails) {
        TourGuide updatedTourGuide = tourGuideService.updateTourGuide(id, tourGuideDetails);
        if (updatedTourGuide == null) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("code", 4040);
            errorResponse.put("message", "Tour guide with ID " + id + " not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
        return ResponseEntity.ok(updatedTourGuide);
    }

    // Xóa tour guide
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTourGuide(@PathVariable Integer id) {
        tourGuideService.deleteTourGuide(id);
        return ResponseEntity.noContent().build();
    }
}