package com.travelvn.tourbookingsytem.controller;

import com.travelvn.tourbookingsytem.dto.request.FestivalAdRequest;
import com.travelvn.tourbookingsytem.dto.response.FestivalAdResponse;
import com.travelvn.tourbookingsytem.service.FestivalAdService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/festivals")
public class FestivalAdontroller {

    @Autowired
    private FestivalAdService festivalService;

    @GetMapping
    public ResponseEntity<List<FestivalAdResponse>> getAllFestivals() {
        return ResponseEntity.ok(festivalService.getAllFestivals());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FestivalAdResponse> getFestivalById(@PathVariable Integer id) {
        FestivalAdResponse festival = festivalService.getFestivalById(id);
        return festival != null ? ResponseEntity.ok(festival) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<FestivalAdResponse> createFestival(@Valid @RequestBody FestivalAdRequest festivalAdRequest) {
        return ResponseEntity.ok(festivalService.createFestival(festivalAdRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FestivalAdResponse> updateFestival(@PathVariable Integer id, @Valid @RequestBody FestivalAdRequest festivalAdRequest) {
        FestivalAdResponse updatedFestival = festivalService.updateFestival(id, festivalAdRequest);
        return updatedFestival != null ? ResponseEntity.ok(updatedFestival) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFestival(@PathVariable Integer id) {
        festivalService.deleteFestival(id);
        return ResponseEntity.noContent().build();
    }
}