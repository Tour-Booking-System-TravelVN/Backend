package com.travelvn.tourbookingsytem.service;

import com.travelvn.tourbookingsytem.dto.request.TourAdRequest;
import com.travelvn.tourbookingsytem.dto.response.TourAdResponse;
import com.travelvn.tourbookingsytem.mapper.TourMapper;
import com.travelvn.tourbookingsytem.model.Category;
import com.travelvn.tourbookingsytem.model.Tour;
import com.travelvn.tourbookingsytem.model.TourOperator;
import com.travelvn.tourbookingsytem.repository.CategoryRepository;
import com.travelvn.tourbookingsytem.repository.TourOperatorRepository;
import com.travelvn.tourbookingsytem.repository.TourRepository;
import com.travelvn.tourbookingsytem.util.TourIdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TourAdService {

    private static final Logger logger = LoggerFactory.getLogger(TourAdService.class);
    private static final int MAX_ATTEMPTS = 10;
    @Autowired
    private TourOperatorRepository tourOperatorRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TourRepository tourRepository;

    @Autowired
    private TourIdGenerator tourIdGenerator;

    @Autowired
    private TourMapper tourMapper;

    public List<TourAdResponse> getAllTours() {
        logger.info("Fetching all tours");
        List<Tour> tours = tourRepository.findAll();
        logger.debug("Found {} tours", tours.size());
        return tours.stream().map(tourMapper::toResponse).collect(Collectors.toList());
    }

    public TourAdResponse getTourById(String id) {
        if (id == null || id.trim().isEmpty()) {
            logger.error("Tour ID must not be null or empty");
            throw new IllegalArgumentException("Tour ID must not be null or empty");
        }

        logger.info("Fetching tour with ID: {}", id);
        Optional<Tour> tour = tourRepository.findByTourId(id);
        if (tour.isPresent()) {
            logger.debug("Found tour: {}", tour.get());
            return tourMapper.toResponse(tour.get());
        } else {
            logger.warn("Tour with ID {} not found", id);
            throw new RuntimeException("Tour with ID " + id + " not found");
        }
    }
    public Optional<Tour> searchTourById(String tourId) {
        logger.info("Searching tour by ID: {}", tourId);
        return tourRepository.findByTourId(tourId);
    }

    public Optional<Tour> findById(String id) {
        logger.info("Searching tour by ID: {}", id);
        return tourRepository.findById(id);
    }

    public List<Tour> searchByNameOrLocation(String keyword) {
        logger.info("Searching tours by keyword (name/place): {}", keyword);
        return tourRepository.searchByNameOrPlace(keyword);
    }

    public List<TourAdResponse> searchToursByLocationOrName(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            logger.warn("Search keyword must not be null or empty");
            throw new IllegalArgumentException("Search keyword must not be null or empty");
        }

        logger.info("Searching tours with keyword: {}", keyword);
        List<Tour> tours = tourRepository.findByLocationOrTourNameContaining(keyword.trim());
        logger.debug("Found {} tours for keyword '{}'", tours.size(), keyword);
        return tours.stream().map(tourMapper::toResponse).collect(Collectors.toList());
    }

    @Transactional
    public TourAdResponse createTour(TourAdRequest tourAdRequest) {
        logger.info("Starting to create Tour from request: {}", tourAdRequest);
        Tour tour = tourMapper.toEntity(tourAdRequest);

        // MUST: fetch managed entities instead of transient
        if (tourAdRequest.getTourOperatorId() != null) {
            TourOperator operator = tourOperatorRepository.findById(tourAdRequest.getTourOperatorId())
                    .orElseThrow(() -> new RuntimeException("TourOperator not found"));
            tour.setTourOperator(operator);
        }

        if (tourAdRequest.getLastUpdatedOperator() != null) {
            TourOperator updater = tourOperatorRepository.findById(tourAdRequest.getLastUpdatedOperator())
                    .orElseThrow(() -> new RuntimeException("LastUpdatedOperator not found"));
            tour.setLastUpdatedOperator(updater);
        }

        if (tourAdRequest.getCategoryId() != null) {
            Category category = categoryRepository.findById(tourAdRequest.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            tour.setCategory(category);
        }

        int attempts = 0;
        while (attempts < MAX_ATTEMPTS) {
            try {
                String tourId = tourIdGenerator.generateTourId(tour.getDuration());
                tour.setTourId(tourId);
                Tour savedTour = tourRepository.save(tour);
                return tourMapper.toResponse(savedTour);
            } catch (DataIntegrityViolationException e) {
                attempts++;
                if (attempts >= MAX_ATTEMPTS) {
                    throw new RuntimeException("Failed to generate unique tourId");
                }
            }
        }

        throw new IllegalStateException("Unexpected error during tour creation");
    }



    @Transactional
    public TourAdResponse updateTour(String id, TourAdRequest tourAdRequest) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Tour ID must not be null or empty");
        }

        Optional<Tour> tourOptional = tourRepository.findByTourId(id); // SỬA Ở ĐÂY ✅

        if (tourOptional.isPresent()) {
            Tour tour = tourOptional.get();
            tourMapper.updateEntityFromRequest(tourAdRequest, tour);
            Tour updatedTour = tourRepository.save(tour);
            return tourMapper.toResponse(updatedTour);
        } else {
            throw new RuntimeException("Tour with ID " + id + " not found");
        }
    }


    @Transactional
    public void deleteTour(String id) {
        if (id == null || id.trim().isEmpty()) {
            logger.error("Tour ID must not be null or empty");
            throw new IllegalArgumentException("Tour ID must not be null or empty");
        }

        logger.info("Deleting tour with ID: {}", id);
        if (!tourRepository.existsById(id)) {
            logger.warn("Tour with ID {} not found", id);
            throw new RuntimeException("Tour with ID " + id + " not found");
        }

        try {
            tourRepository.deleteById(id);
            logger.info("Successfully deleted tour with ID: {}", id);
        } catch (Exception e) {
            logger.error("Failed to delete tour with ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("tour.delete.failed: " + e.getMessage(), e);
        }
    }
}