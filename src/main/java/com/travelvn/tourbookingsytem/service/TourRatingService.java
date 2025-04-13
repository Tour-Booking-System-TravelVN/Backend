package com.travelvn.tourbookingsytem.service;

import com.travelvn.tourbookingsytem.dto.response.TourRatingResponse;
import com.travelvn.tourbookingsytem.repository.TourRatingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TourRatingService {
    private final TourRatingRepository tourRatingRepository;

    /**
     * Lấy các đánh giá của tour theo mã tour
     * @param tourId
     * @return
     */
    public List<TourRatingResponse> getTourRatingByTour(String tourId) {
        return null;
    }
}
