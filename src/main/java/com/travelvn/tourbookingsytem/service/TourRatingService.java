package com.travelvn.tourbookingsytem.service;
import com.travelvn.tourbookingsytem.model.TourRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.travelvn.tourbookingsytem.repository.TourRatingRepository;
import java.util.List;

@Service
public class TourRatingService {
    @Autowired
    private TourRatingRepository tourRatingRepository;

    public List<TourRating> getAllRatings() {
        return tourRatingRepository.findAll();
    }
// sua danh gia nam tawng maketting :)))
    public TourRating updateRatingStatus(Integer id, String status) {
        TourRating rating = tourRatingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rating not found"));
        rating.setStatus(status);
        return tourRatingRepository.save(rating);
    }
}