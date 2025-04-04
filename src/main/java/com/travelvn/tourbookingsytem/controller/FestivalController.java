package com.travelvn.tourbookingsytem.controller;


import com.travelvn.tourbookingsytem.model.Festival;
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
    public ResponseEntity<List<Festival>> getAllFestivals() {
        return ResponseEntity.ok(festivalService.getAllFestivals());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Festival> getFestivalById(@PathVariable Integer id) {
        Festival festival = festivalService.getFestivalById(id);
        if (festival != null) {
            return ResponseEntity.ok(festival);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Festival> createFestival(@Valid @RequestBody Festival festival) {
        return ResponseEntity.ok(festivalService.createFestival(festival));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Festival> updateFestival(@PathVariable Integer id, @Valid @RequestBody Festival festivalDetails) {
        Festival updatedFestival = festivalService.updateFestival(id, festivalDetails);
        if (updatedFestival != null) {
            return ResponseEntity.ok(updatedFestival);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFestival(@PathVariable Integer id) {
        festivalService.deleteFestival(id);
        return ResponseEntity.noContent().build();
    }
}