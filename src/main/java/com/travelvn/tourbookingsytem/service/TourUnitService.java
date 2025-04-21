package com.travelvn.tourbookingsytem.service;

import com.travelvn.tourbookingsytem.model.TourUnit;
import com.travelvn.tourbookingsytem.repository.TourUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class TourUnitService {

    @Autowired
    private TourUnitRepository tourUnitRepository;

    @Transactional(readOnly = true)
    public List<TourUnit> getAllTourUnits() {
        return tourUnitRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<TourUnit> getTourUnitById(String tourUnitId) {
        return tourUnitRepository.findById(tourUnitId);
    }

    @Transactional
    public TourUnit createTourUnit(TourUnit tourUnit) {
        // Kiểm tra nếu tourUnitId chưa được cung cấp, tự động sinh
        if (tourUnit.getTourUnitId() == null || tourUnit.getTourUnitId().isEmpty()) {
            String tourId = tourUnit.getTour().getTourId();
            String newTourUnitId = generateNextTourUnitId(tourId);
            tourUnit.setTourUnitId(newTourUnitId);
        }
        tourUnit.setCreatedTime(Instant.now());
        tourUnit.setLastUpdatedTime(Instant.now());
        return tourUnitRepository.save(tourUnit);
    }

    @Transactional
    public Optional<TourUnit> updateTourUnit(String tourUnitId, TourUnit tourUnitDetails) {
        return tourUnitRepository.findById(tourUnitId).map(tourUnit -> {
            tourUnit.setFestival(tourUnitDetails.getFestival());
            tourUnit.setTour(tourUnitDetails.getTour());
            tourUnit.setDiscount(tourUnitDetails.getDiscount());
            tourUnit.setTourOperator(tourUnitDetails.getTourOperator());
            tourUnit.setLastUpdatedOperator(tourUnitDetails.getLastUpdatedOperator());
            tourUnit.setDepartureDate(tourUnitDetails.getDepartureDate());
            tourUnit.setReturnDate(tourUnitDetails.getReturnDate());
            tourUnit.setAdultTourPrice(tourUnitDetails.getAdultTourPrice());
            tourUnit.setChildTourPrice(tourUnitDetails.getChildTourPrice());
            tourUnit.setToddlerTourPrice(tourUnitDetails.getToddlerTourPrice());
            tourUnit.setBabyTourPrice(tourUnitDetails.getBabyTourPrice());
            tourUnit.setAdultTourCost(tourUnitDetails.getAdultTourCost());
            tourUnit.setChildTourCost(tourUnitDetails.getChildTourCost());
            tourUnit.setToddlerTourCost(tourUnitDetails.getToddlerTourCost());
            tourUnit.setBabyTourCost(tourUnitDetails.getBabyTourCost());
            tourUnit.setPrivateRoomPrice(tourUnitDetails.getPrivateRoomPrice());
            tourUnit.setLastUpdatedTime(Instant.now());
            tourUnit.setMaximumCapacity(tourUnitDetails.getMaximumCapacity());
            tourUnit.setAvailableCapacity(tourUnitDetails.getAvailableCapacity());
            tourUnit.setTotalAdditionalCost(tourUnitDetails.getTotalAdditionalCost());
            tourUnit.setGuideSet(tourUnitDetails.getGuideSet());
            tourUnit.setBookingSet(tourUnitDetails.getBookingSet());
            tourUnit.setRatingSet(tourUnitDetails.getRatingSet());
            return tourUnitRepository.save(tourUnit);
        });
    }

    @Transactional
    public boolean deleteTourUnit(String tourUnitId) {
        return tourUnitRepository.findById(tourUnitId)
                .map(tourUnit -> {
                    tourUnitRepository.delete(tourUnit);
                    return true;
                }).orElse(false);
    }

    private String generateNextTourUnitId(String tourId) {
        Optional<String> maxTourUnitId = tourUnitRepository.findMaxTourUnitIdByTourIdPrefix(tourId);
        int nextNumber = 0;
        if (maxTourUnitId.isPresent()) {
            String maxId = maxTourUnitId.get();
            String numberPart = maxId.substring(maxId.lastIndexOf('-') + 1);
            try {
                nextNumber = Integer.parseInt(numberPart) + 1;
            } catch (NumberFormatException e) {
                nextNumber = 0;
            }
        }
        // Định dạng thành 6 chữ số
        String formattedNumber = String.format("%06d", nextNumber);
        return tourId + "-" + formattedNumber;
    }
}