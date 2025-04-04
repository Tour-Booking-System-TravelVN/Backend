package com.travelvn.tourbookingsytem.service;


import com.travelvn.tourbookingsytem.model.TourOperator;
import com.travelvn.tourbookingsytem.repository.TourOperatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TourOperatorService {
    @Autowired
    private TourOperatorRepository tourOperatorRepository;

    public TourOperator createTourOperator(TourOperator tourOperator) {
        return tourOperatorRepository.save(tourOperator);
    }

    public TourOperator updateTourOperator(Integer id, TourOperator updatedTourOperator) {
        TourOperator existing = tourOperatorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tour Operator not found"));
        existing.setFirstname(updatedTourOperator.getFirstname());
        existing.setLastname(updatedTourOperator.getLastname());
        existing.setDateOfBirth(updatedTourOperator.getDateOfBirth());
        existing.setGender(updatedTourOperator.getGender());
        existing.setAddress(updatedTourOperator.getAddress());
        existing.setPhoneNumber(updatedTourOperator.getPhoneNumber());
        existing.setCitizenId(updatedTourOperator.getCitizenId());
        existing.setHometown(updatedTourOperator.getHometown());
        existing.setSalary(updatedTourOperator.getSalary());
        existing.setStartDate(updatedTourOperator.getStartDate());
        existing.setEndDate(updatedTourOperator.getEndDate());
        return tourOperatorRepository.save(existing);
    }

    public void deleteTourOperator(Integer id) {
        tourOperatorRepository.deleteById(id);
    }

    public List<TourOperator> getAllTourOperators() {
        return tourOperatorRepository.findAll();
    }
}