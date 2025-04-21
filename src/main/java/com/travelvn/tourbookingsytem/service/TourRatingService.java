package com.travelvn.tourbookingsytem.service;

import com.travelvn.tourbookingsytem.model.TourRating;
import com.travelvn.tourbookingsytem.repository.TourRatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TourRatingService {

    @Autowired
    private TourRatingRepository tourRatingRepository;

    // Lấy tất cả đánh giá
    public List<TourRating> getAllTourRatings() {
        return tourRatingRepository.findAll();
    }

    // Cập nhật trạng thái đánh giá (Duyệt hoặc Không duyệt)
    @Transactional
    public TourRating updateTourRatingStatus(Integer id, String status) {
        Optional<TourRating> ratingOptional = tourRatingRepository.findById(id);
        if (ratingOptional.isPresent()) {
            TourRating rating = ratingOptional.get();
            if (!" chờ duyệt".equals(rating.getStatus())) {
                throw new IllegalStateException("Chỉ có thể cập nhật trạng thái cho đánh giá đang chờ duyệt!");
            }
            if (!"đã duyệt".equals(status) && !"từ chối duyệt".equals(status)) {
                throw new IllegalArgumentException("Trạng thái không hợp lệ! Phải là 'đã duyệt' hoặc 'từ chối duyệt'.");
            }
            rating.setStatus(status);
            return tourRatingRepository.save(rating);
        } else {
            throw new RuntimeException("Không tìm thấy đánh giá với ID: " + id);
        }
    }
}