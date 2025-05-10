package com.travelvn.tourbookingsytem.service;

import com.travelvn.tourbookingsytem.dto.request.TourGuideRequest;
import com.travelvn.tourbookingsytem.dto.response.TourGuideResponse;
import com.travelvn.tourbookingsytem.mapper.TourGuideMapper;
import com.travelvn.tourbookingsytem.model.TourGuide;
import com.travelvn.tourbookingsytem.repository.TourGuideRepository;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TourGuideService {

    @Autowired
    private TourGuideRepository tourGuideRepository;

    @Autowired
    private TourGuideMapper tourGuideMapper;

    @Autowired
    private UserAccountFactoryService userAccountFactoryService;

    public List<TourGuideResponse> getAllTourGuides() {
        return tourGuideRepository.findAll().stream()
                .map(tourGuideMapper::toResponse)
                .collect(Collectors.toList());
    }

    public TourGuideResponse getTourGuideById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Tour guide ID must not be null");
        }
        Optional<TourGuide> tourGuide = tourGuideRepository.findById(id);
        return tourGuide.map(tourGuideMapper::toResponse).orElse(null);
    }

    public List<TourGuideResponse> searchTourGuides(String firstname, String lastname) {
        List<TourGuide> tourGuides;

        if (firstname != null && lastname != null) {
            tourGuides = tourGuideRepository.findByFirstnameContainingIgnoreCaseAndLastnameContainingIgnoreCase(firstname, lastname);
        } else if (firstname != null) {
            tourGuides = tourGuideRepository.findByFirstnameContainingIgnoreCase(firstname);
        } else if (lastname != null) {
            tourGuides = tourGuideRepository.findByLastnameContainingIgnoreCase(lastname);
        } else {
            tourGuides = tourGuideRepository.findAll();
        }

        return tourGuides.stream()
                .map(tourGuideMapper::toResponse)
                .collect(Collectors.toList());
    }


    public TourGuideResponse createTourGuide(TourGuideRequest tourGuideRequest) {
        if (tourGuideRequest.getFirstname() == null) {
            throw new IllegalArgumentException("Firstname must not be null");
        }
        if (tourGuideRequest.getLastname() == null) {
            throw new IllegalArgumentException("Lastname must not be null");
        }
        if (tourGuideRequest.getDateOfBirth() == null) {
            throw new IllegalArgumentException("Date of birth must not be null");
        }
        if (tourGuideRequest.getGender() == null) {
            throw new IllegalArgumentException("Gender must not be null");
        }
        if (tourGuideRequest.getAddress() == null) {
            throw new IllegalArgumentException("Address must not be null");
        }
        if (tourGuideRequest.getPhoneNumber() == null) {
            throw new IllegalArgumentException("Phone number must not be null");
        }
        if (tourGuideRequest.getCitizenId() == null) {
            throw new IllegalArgumentException("Citizen ID must not be null");
        }
        if (tourGuideRequest.getHometown() == null) {
            throw new IllegalArgumentException("Hometown must not be null");
        }
        if (tourGuideRequest.getSalary() == null) {
            throw new IllegalArgumentException("Salary must not be null");
        }
        if (tourGuideRequest.getStartDate() == null) {
            throw new IllegalArgumentException("Start date must not be null");
        }
        if (tourGuideRequest.getCardId() == null) {
            throw new IllegalArgumentException("Card ID must not be null");
        }
        if (tourGuideRequest.getLanguage() == null) {
            throw new IllegalArgumentException("Language must not be null");
        }

        TourGuide tourGuide = tourGuideMapper.toEntity(tourGuideRequest);
        tourGuide.setId(null); // Đảm bảo ID tự sinh
        TourGuide savedTourGuide = tourGuideRepository.save(tourGuide);
        userAccountFactoryService.createForTourGuide(savedTourGuide);
        return tourGuideMapper.toResponse(savedTourGuide);
    }

    public TourGuideResponse updateTourGuide(Integer id, TourGuideRequest tourGuideRequest) {
        if (id == null) {
            throw new IllegalArgumentException("Tour guide ID must not be null");
        }
        Optional<TourGuide> tourGuideOptional = tourGuideRepository.findById(id);
        if (tourGuideOptional.isPresent()) {
            TourGuide tourGuide = tourGuideOptional.get();
            tourGuideMapper.updateEntityFromRequest(tourGuideRequest, tourGuide);

            // Kiểm tra salary không âm
            if (tourGuide.getSalary() != null && tourGuide.getSalary().compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("Salary must not be negative");
            }

            TourGuide updatedTourGuide = tourGuideRepository.save(tourGuide);
            return tourGuideMapper.toResponse(updatedTourGuide);
        }
        return null;
    }

    public void deleteTourGuide(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Tour guide ID must not be null");
        }
        tourGuideRepository.deleteById(id);
    }
}