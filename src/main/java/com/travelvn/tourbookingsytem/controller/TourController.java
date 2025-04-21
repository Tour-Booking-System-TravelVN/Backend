package com.travelvn.tourbookingsytem.controller;

import com.travelvn.tourbookingsytem.dto.request.TourRequest;
import com.travelvn.tourbookingsytem.model.Category;
import com.travelvn.tourbookingsytem.model.Tour;
import com.travelvn.tourbookingsytem.model.TourOperator;
import com.travelvn.tourbookingsytem.repository.CategoryRepository;
import com.travelvn.tourbookingsytem.repository.TourOperatorRepository;
import com.travelvn.tourbookingsytem.service.TourService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
//xong
@RestController
@RequestMapping("/api/tours")
public class TourController {

    private static final Logger logger = LoggerFactory.getLogger(TourController.class);

    @Autowired
    private TourService tourService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TourOperatorRepository tourOperatorRepository;

    // Lấy danh sách tất cả tour
    @GetMapping
    public ResponseEntity<List<Tour>> getAllTours() {
        logger.info("Fetching all tours");
        List<Tour> tours = tourService.getAllTours();
        return ResponseEntity.ok(tours);
    }

    // Lấy thông tin tour theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Tour> getTourById(@PathVariable String id) {
        logger.info("Fetching tour with ID: {}", id);
        try {
            Tour tour = tourService.getTourById(id);
            return ResponseEntity.ok(tour);
        } catch (RuntimeException e) {
            logger.warn("Tour with ID {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }

    // Thêm tour mới
    @PostMapping
    public ResponseEntity<Tour> createTour(@Valid @RequestBody TourRequest tourRequest) {
        logger.info("Creating tour from request: {}", tourRequest);

        // Kiểm tra và ánh xạ các khóa phụ
        Category category = categoryRepository.findById(tourRequest.getCategoryId())
                .orElseThrow(() -> {
                    logger.error("Category with ID {} does not exist", tourRequest.getCategoryId());
                    return new IllegalArgumentException("Category with ID " + tourRequest.getCategoryId() + " does not exist");
                });

        TourOperator tourOperator = tourOperatorRepository.findById(tourRequest.getTourOperatorId())
                .orElseThrow(() -> {
                    logger.error("Tour Operator with ID {} does not exist", tourRequest.getTourOperatorId());
                    return new IllegalArgumentException("Tour Operator with ID " + tourRequest.getTourOperatorId() + " does not exist");
                });

        TourOperator lastUpdatedOperator = null;
        if (tourRequest.getLastUpdatedOperator() != null) {
            lastUpdatedOperator = tourOperatorRepository.findById(tourRequest.getLastUpdatedOperator())
                    .orElseThrow(() -> {
                        logger.error("Last Updated Operator with ID {} does not exist", tourRequest.getLastUpdatedOperator());
                        return new IllegalArgumentException("Last Updated Operator with ID " + tourRequest.getLastUpdatedOperator() + " does not exist");
                    });
        }

        // Ánh xạ thủ công từ TourRequest sang Tour
        Tour tour = new Tour();
        tour.setCategory(category);
        tour.setTourOperator(tourOperator);
        tour.setLastUpdatedOperator(lastUpdatedOperator);
        tour.setTourName(tourRequest.getTourName());
        tour.setDuration(tourRequest.getDuration());
        tour.setVehicle(tourRequest.getVehicle());
        tour.setTargetAudience(tourRequest.getTargetAudience());
        tour.setDeparturePlace(tourRequest.getDeparturePlace());
        tour.setPlacesToVisit(tourRequest.getPlacesToVisit());
        tour.setCuisine(tourRequest.getCuisine());
        tour.setIdealTime(tourRequest.getIdealTime());
        tour.setDescription(tourRequest.getDescription());
        tour.setCreatedTime(tourRequest.getCreatedTime());
        tour.setLastUpdatedTime(tourRequest.getLastUpdatedTime());
        tour.setInclusions(tourRequest.getInclusions());
        tour.setExclusions(tourRequest.getExclusions());

        Tour savedTour = tourService.createTour(tour);
        return ResponseEntity.ok(savedTour);
    }

    // Cập nhật thông tin tour
    @PutMapping("/{id}")
    public ResponseEntity<Tour> updateTour(@PathVariable String id, @Valid @RequestBody TourRequest tourRequest) {
        logger.info("Updating tour with ID: {}", id);

        // Kiểm tra và ánh xạ các khóa phụ
        Category category = categoryRepository.findById(tourRequest.getCategoryId())
                .orElseThrow(() -> {
                    logger.error("Category with ID {} does not exist", tourRequest.getCategoryId());
                    return new IllegalArgumentException("Category with ID " + tourRequest.getCategoryId() + " does not exist");
                });

        TourOperator tourOperator = tourOperatorRepository.findById(tourRequest.getTourOperatorId())
                .orElseThrow(() -> {
                    logger.error("Tour Operator with ID {} does not exist", tourRequest.getTourOperatorId());
                    return new IllegalArgumentException("Tour Operator with ID " + tourRequest.getTourOperatorId() + " does not exist");
                });

        TourOperator lastUpdatedOperator = null;
        if (tourRequest.getLastUpdatedOperator() != null) {
            lastUpdatedOperator = tourOperatorRepository.findById(tourRequest.getLastUpdatedOperator())
                    .orElseThrow(() -> {
                        logger.error("Last Updated Operator with ID {} does not exist", tourRequest.getLastUpdatedOperator());
                        return new IllegalArgumentException("Last Updated Operator with ID " + tourRequest.getLastUpdatedOperator() + " does not exist");
                    });
        }

        // Ánh xạ thủ công từ TourRequest sang Tour
        Tour tour = new Tour();
        tour.setTourId(id); // Đặt tourId để cập nhật đúng bản ghi
        tour.setCategory(category);
        tour.setTourOperator(tourOperator);
        tour.setLastUpdatedOperator(lastUpdatedOperator);
        tour.setTourName(tourRequest.getTourName());
        tour.setDuration(tourRequest.getDuration());
        tour.setVehicle(tourRequest.getVehicle());
        tour.setTargetAudience(tourRequest.getTargetAudience());
        tour.setDeparturePlace(tourRequest.getDeparturePlace());
        tour.setPlacesToVisit(tourRequest.getPlacesToVisit());
        tour.setCuisine(tourRequest.getCuisine());
        tour.setIdealTime(tourRequest.getIdealTime());
        tour.setDescription(tourRequest.getDescription());
        tour.setCreatedTime(tourRequest.getCreatedTime());
        tour.setLastUpdatedTime(tourRequest.getLastUpdatedTime());
        tour.setInclusions(tourRequest.getInclusions());
        tour.setExclusions(tourRequest.getExclusions());

        try {
            Tour updatedTour = tourService.updateTour(id, tour);
            return ResponseEntity.ok(updatedTour);
        } catch (RuntimeException e) {
            logger.warn("Tour with ID {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }
    //Tìm theo dia danh hoac ten
    @GetMapping("/search")
    public ResponseEntity<List<Tour>> searchToursByLocationOrName(@RequestParam String keyword) {
        return ResponseEntity.ok(tourService.searchToursByLocationOrName(keyword));
    }
    // Xóa tour
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