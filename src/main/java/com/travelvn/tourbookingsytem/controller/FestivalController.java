package com.travelvn.tourbookingsytem.controller;

import com.travelvn.tourbookingsytem.dto.request.FestivalRequest;
import com.travelvn.tourbookingsytem.dto.response.FestivalResponse;
import com.travelvn.tourbookingsytem.service.FestivalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/festivals")
public class FestivalController {

    @Autowired
    private FestivalService festivalService;

    @GetMapping
    public ResponseEntity<List<FestivalResponse>> getAllFestivals() {
        return ResponseEntity.ok(festivalService.getAllFestivals());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FestivalResponse> getFestivalById(@PathVariable Integer id) {
        FestivalResponse festival = festivalService.getFestivalById(id);
        return festival != null ? ResponseEntity.ok(festival) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<FestivalResponse> createFestival(@Valid @RequestBody FestivalRequest festivalRequest) {
        return ResponseEntity.ok(festivalService.createFestival(festivalRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FestivalResponse> updateFestival(@PathVariable Integer id, @Valid @RequestBody FestivalRequest festivalRequest) {
        FestivalResponse updatedFestival = festivalService.updateFestival(id, festivalRequest);
        return updatedFestival != null ? ResponseEntity.ok(updatedFestival) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFestival(@PathVariable Integer id) {
        festivalService.deleteFestival(id);
        return ResponseEntity.noContent().build();
    }
}