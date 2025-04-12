package com.travelvn.tourbookingsytem.service;


import com.travelvn.tourbookingsytem.model.Tour;
import com.travelvn.tourbookingsytem.repository.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TourService {

    @Autowired
    private TourRepository tourRepository;

    // Lấy danh sách tất cả tour
    public List<Tour> getAllTours() {
        return tourRepository.findAll();
    }

    // Lấy thông tin tour theo ID
    public Tour getTourById(String id) {
        Optional<Tour> tour = tourRepository.findById(id);
        return tour.orElse(null);
    }

    // Thêm tour mới
    public Tour createTour(Tour tour) {
        return tourRepository.save(tour);
    }

    // Cập nhật thông tin tour
    public Tour updateTour(String id, Tour tourDetails) {
        Optional<Tour> tourOptional = tourRepository.findById(id);
        if (tourOptional.isPresent()) {
            Tour tour = tourOptional.get();
            tour.setTourName(tourDetails.getTourName());
            tour.setDescription(tourDetails.getDescription());
            tour.setDuration(tourDetails.getDuration());
            tour.setVehicle(tourDetails.getVehicle());
            tour.setTargetAudience(tourDetails.getTargetAudience());
            tour.setDeparturePlace(tourDetails.getDeparturePlace());
            tour.setPlacesToVisit(tourDetails.getPlacesToVisit());
            tour.setCuisine(tourDetails.getCuisine());
            tour.setCreatedTime(tourDetails.getCreatedTime());
            tour.setLastUpdatedTime(tourDetails.getLastUpdatedTime());
            tour.setInclusions(tourDetails.getInclusions());
            tour.setExclusions(tourDetails.getExclusions());
            tour.setIdealTime(tourDetails.getIdealTime());
            tour.setTourOperator(tourDetails.getTourOperator());
            return tourRepository.save(tour);
        }
        return null;
    }

    // Xóa tour
    public void deleteTour(String id) {
        tourRepository.deleteById(id);
    }
}