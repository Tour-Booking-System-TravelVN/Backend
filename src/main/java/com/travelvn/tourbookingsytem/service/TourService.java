package com.travelvn.tourbookingsytem.service;

import com.travelvn.tourbookingsytem.model.Tour;
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

@Service
public class TourService {

    private static final Logger logger = LoggerFactory.getLogger(TourService.class);
    private static final int MAX_ATTEMPTS = 10;

    @Autowired
    private TourRepository tourRepository;

    @Autowired
    private TourIdGenerator tourIdGenerator;

    // Lấy danh sách tất cả tour
    public List<Tour> getAllTours() {
        logger.info("Fetching all tours");
        List<Tour> tours = tourRepository.findAll();
        logger.debug("Found {} tours", tours.size());
        return tours;
    }

    // Lấy thông tin tour theo ID
    public Tour getTourById(String id) {
        if (id == null || id.trim().isEmpty()) {
            logger.error("Tour ID must not be null or empty");
            throw new IllegalArgumentException("Tour ID must not be null or empty");
        }

        logger.info("Fetching tour with ID: {}", id);
        Optional<Tour> tour = tourRepository.findByTourId(id); // Sử dụng phương thức mới
        if (tour.isPresent()) {
            logger.debug("Found tour: {}", tour.get());
            return tour.get();
        } else {
            logger.warn("Tour with ID {} not found", id);
            throw new RuntimeException("Tour with ID " + id + " not found");
        }
    }

    // Tìm tour theo tên địa danh hoặc tên tour
    public List<Tour> searchToursByLocationOrName(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            logger.warn("Search keyword must not be null or empty");
            throw new IllegalArgumentException("Search keyword must not be null or empty");
        }

        logger.info("Searching tours with keyword: {}", keyword);
        List<Tour> tours = tourRepository.findByLocationOrTourNameContaining(keyword.trim());
        logger.debug("Found {} tours for keyword '{}'", tours.size(), keyword);
        return tours;
    }

    // Thêm tour mới
    @Transactional
    public Tour createTour(Tour tour) {
        if (tour == null) {
            logger.error("Tour must not be null");
            throw new IllegalArgumentException("Tour must not be null");
        }
        if (tour.getDuration() == null || tour.getDuration().trim().isEmpty()) {
            logger.error("Duration must not be null or empty");
            throw new IllegalArgumentException("Duration must not be null or empty");
        }

        logger.info("Starting to create Tour: {}", tour);

        // Đảm bảo tourId là null để tự sinh
        tour.setTourId(null);

        // Tạo tourId và lưu, thử lại nếu trùng lặp
        int attempts = 0;
        while (attempts < MAX_ATTEMPTS) {
            try {
                // Truyền duration để sinh tourId
                String tourId = tourIdGenerator.generateTourId(tour.getDuration());
                logger.debug("Generated tourId: {}", tourId);
                tour.setTourId(tourId);

                // Lưu vào database
                logger.debug("Saving Tour to database: {}", tour);
                Tour savedTour = tourRepository.save(tour);
                logger.info("Successfully saved Tour with ID: {}", savedTour.getTourId());
                return savedTour;
            } catch (DataIntegrityViolationException e) {
                logger.warn("TourId {} already exists, retrying... Attempt {}", tour.getTourId(), attempts + 1);
                attempts++;
                if (attempts >= MAX_ATTEMPTS) {
                    logger.error("Failed to generate a unique tourId after {} attempts", MAX_ATTEMPTS);
                    throw new RuntimeException("tour.id.generation.failed");
                }
            } catch (Exception e) {
                logger.error("Failed to save Tour: {}", e.getMessage(), e);
                throw new RuntimeException("tour.save.failed: " + e.getMessage(), e);
            }
        }

        return null;
    }

    // Cập nhật thông tin tour
    @Transactional
    public Tour updateTour(String id, Tour tourDetails) {
        if (id == null || id.trim().isEmpty()) {
            logger.error("Tour ID must not be null or empty");
            throw new IllegalArgumentException("Tour ID must not be null or empty");
        }
        if (tourDetails == null) {
            logger.error("Tour details must not be null");
            throw new IllegalArgumentException("Tour details must not be null");
        }

        logger.info("Updating tour with ID: {}", id);
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

            logger.debug("Saving updated tour: {}", tour);
            Tour updatedTour = tourRepository.save(tour);
            logger.info("Successfully updated tour with ID: {}", updatedTour.getTourId());
            return updatedTour;
        } else {
            logger.warn("Tour with ID {} not found", id);
            throw new RuntimeException("Tour with ID " + id + " not found");
        }
    }

    // Xóa tour
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