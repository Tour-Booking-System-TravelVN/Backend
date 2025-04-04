package com.travelvn.tourbookingsytem.service;


import com.travelvn.tourbookingsytem.model.TourGuide;
import com.travelvn.tourbookingsytem.repository.TourGuideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TourGuideService {

    @Autowired
    private TourGuideRepository tourGuideRepository;

    public List<TourGuide> getAllTourGuides() {
        return tourGuideRepository.findAll();
    }

    public List<TourGuide> searchTourGuides(String firstname, String lastname) {
        return tourGuideRepository.findByFirstnameAndLastname(firstname, lastname);
    }
}