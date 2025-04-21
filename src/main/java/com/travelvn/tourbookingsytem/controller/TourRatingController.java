package com.travelvn.tourbookingsytem.controller;
import com.travelvn.tourbookingsytem.dto.request.TourRatingStatusUpdate;
import com.travelvn.tourbookingsytem.model.TourRating;
import com.travelvn.tourbookingsytem.service.TourRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/tour-ratings")
public class TourRatingController {

    @Autowired
    private TourRatingService tourRatingService;

    // Lấy tất cả đánh giá
    @GetMapping
    public ResponseEntity<List<TourRating>> getAllTourRatings() {
        List<TourRating> tourRatings = tourRatingService.getAllTourRatings();
        return ResponseEntity.ok(tourRatings);
    }

    // Cập nhật trạng thái đánh giá (Duyệt hoặc Không duyệt)
    @PutMapping("/{id}/status")
    public ResponseEntity<TourRating> updateTourRatingStatus(@PathVariable Integer id, @RequestBody TourRatingStatusUpdate statusUpdate) {
        TourRating updatedRating = tourRatingService.updateTourRatingStatus(id, statusUpdate.getStatus());
        return ResponseEntity.ok(updatedRating);
    }
}
