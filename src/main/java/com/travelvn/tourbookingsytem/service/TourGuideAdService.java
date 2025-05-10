package com.travelvn.tourbookingsytem.service;

import com.travelvn.tourbookingsytem.dto.request.TourGuideAdRequest;
import com.travelvn.tourbookingsytem.dto.response.TourGuideAdResponse;
import com.travelvn.tourbookingsytem.mapper.TourGuideMapper;
import com.travelvn.tourbookingsytem.model.TourGuide;
import com.travelvn.tourbookingsytem.repository.TourGuideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TourGuideAdService {

    @Autowired
    private TourGuideRepository tourGuideRepository;

    @Autowired
    private TourGuideMapper tourGuideMapper;

    @Autowired
    private UserAccountFactoryService userAccountFactoryService;

    public List<TourGuideAdResponse> getAllTourGuides() {
        return tourGuideRepository.findAll().stream()
                .map(tourGuideMapper::toResponse)
                .collect(Collectors.toList());
    }

    public TourGuideAdResponse getTourGuideById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Tour guide ID must not be null");
        }
        Optional<TourGuide> tourGuide = tourGuideRepository.findById(id);
        return tourGuide.map(tourGuideMapper::toResponse).orElse(null);
    }

    public List<TourGuideAdResponse> searchTourGuides(String firstname, String lastname) {
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


    public TourGuideAdResponse createTourGuide(TourGuideAdRequest tourGuideAdRequest) {
        if (tourGuideAdRequest.getFirstname() == null) {
            throw new IllegalArgumentException("Firstname must not be null");
        }
        if (tourGuideAdRequest.getLastname() == null) {
            throw new IllegalArgumentException("Lastname must not be null");
        }
        if (tourGuideAdRequest.getDateOfBirth() == null) {
            throw new IllegalArgumentException("Date of birth must not be null");
        }
        if (tourGuideAdRequest.getGender() == null) {
            throw new IllegalArgumentException("Gender must not be null");
        }
        if (tourGuideAdRequest.getAddress() == null) {
            throw new IllegalArgumentException("Address must not be null");
        }
        if (tourGuideAdRequest.getPhoneNumber() == null) {
            throw new IllegalArgumentException("Phone number must not be null");
        }
        if (tourGuideAdRequest.getCitizenId() == null) {
            throw new IllegalArgumentException("Citizen ID must not be null");
        }
        if (tourGuideAdRequest.getHometown() == null) {
            throw new IllegalArgumentException("Hometown must not be null");
        }
        if (tourGuideAdRequest.getSalary() == null) {
            throw new IllegalArgumentException("Salary must not be null");
        }
        if (tourGuideAdRequest.getStartDate() == null) {
            throw new IllegalArgumentException("Start date must not be null");
        }
        if (tourGuideAdRequest.getCardId() == null) {
            throw new IllegalArgumentException("Card ID must not be null");
        }
        if (tourGuideAdRequest.getLanguage() == null) {
            throw new IllegalArgumentException("Language must not be null");
        }

        TourGuide tourGuide = tourGuideMapper.toEntity(tourGuideAdRequest);
        tourGuide.setId(null); // Đảm bảo ID tự sinh
        TourGuide savedTourGuide = tourGuideRepository.save(tourGuide);
        userAccountFactoryService.createForTourGuide(savedTourGuide);
        return tourGuideMapper.toResponse(savedTourGuide);
    }

    public TourGuideAdResponse updateTourGuide(Integer id, TourGuideAdRequest tourGuideAdRequest) {
        if (id == null) {
            throw new IllegalArgumentException("Tour guide ID must not be null");
        }
        Optional<TourGuide> tourGuideOptional = tourGuideRepository.findById(id);
        if (tourGuideOptional.isPresent()) {
            TourGuide tourGuide = tourGuideOptional.get();
            tourGuideMapper.updateEntityFromRequest(tourGuideAdRequest, tourGuide);

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