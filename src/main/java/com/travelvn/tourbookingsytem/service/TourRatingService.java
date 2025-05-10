package com.travelvn.tourbookingsytem.service;

import com.travelvn.tourbookingsytem.dto.response.TourRatingResponse;
import com.travelvn.tourbookingsytem.mapper.TourRatingMapper;
import com.travelvn.tourbookingsytem.model.TourRating;
import com.travelvn.tourbookingsytem.repository.TourRatingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TourRatingService {

    private static final Logger logger = LoggerFactory.getLogger(TourRatingService.class);

    @Autowired
    private TourRatingRepository tourRatingRepository;

    @Autowired
    private TourRatingMapper tourRatingMapper;

    public List<TourRatingResponse> getAllTourRatings() {
        logger.info("Fetching all tour ratings");
        List<TourRating> tourRatings = tourRatingRepository.findAll();
        logger.debug("Found {} tour ratings", tourRatings.size());
        return tourRatings.stream()
                .map(tourRatingMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public TourRatingResponse updateTourRatingStatus(Integer id, String status) {
        logger.info("Updating tour rating status for ID: {} to status: {}", id, status);
        Optional<TourRating> ratingOptional = tourRatingRepository.findById(id);
        if (!ratingOptional.isPresent()) {
            logger.warn("Tour rating with ID {} not found", id);
            throw new RuntimeException("Không tìm thấy đánh giá với ID: " + id);
        }
        TourRating rating = ratingOptional.get();
        if (!"chờ duyệt".equals(rating.getStatus())) {
            logger.warn("Cannot update status for tour rating ID {}: current status is '{}', expected 'chờ duyệt'", id, rating.getStatus());
            throw new IllegalStateException("Chỉ có thể cập nhật trạng thái cho đánh giá đang chờ duyệt!");
        }
        if (!"đã duyệt".equals(status) && !"từ chối duyệt".equals(status)) {
            logger.warn("Invalid status '{}' for tour rating ID {}", status, id);
            throw new IllegalArgumentException("Trạng thái không hợp lệ! Phải là 'đã duyệt' hoặc 'từ chối duyệt'.");
        }
        rating.setStatus(status);
        TourRating updatedRating = tourRatingRepository.save(rating);
        logger.info("Successfully updated tour rating ID {} to status: {}", id, status);
        return tourRatingMapper.toResponse(updatedRating);
    }
}