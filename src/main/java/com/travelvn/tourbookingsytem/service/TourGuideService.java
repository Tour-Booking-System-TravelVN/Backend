package com.travelvn.tourbookingsytem.service;

import com.travelvn.tourbookingsytem.model.TourGuide;
import com.travelvn.tourbookingsytem.repository.TourGuideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class TourGuideService {

    @Autowired
    private TourGuideRepository tourGuideRepository;

    // Lấy danh sách tất cả tour guide
    public List<TourGuide> getAllTourGuides() {
        return tourGuideRepository.findAll();
    }

    // Lấy tour guide theo ID
    public TourGuide getTourGuideById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Tour guide ID must not be null");
        }
        Optional<TourGuide> tourGuide = tourGuideRepository.findById(id);
        return tourGuide.orElse(null);
    }

    // Tìm kiếm tour guide theo firstname và lastname
    public List<TourGuide> searchTourGuides(String firstname, String lastname) {
        if (firstname == null && lastname == null) {
            throw new IllegalArgumentException("At least one of firstname or lastname must not be null");
        }

        if (firstname != null && lastname != null) {
            return tourGuideRepository.findByFirstnameAndLastname(firstname, lastname);
        } else if (firstname != null) {
            return tourGuideRepository.findByFirstname(firstname);
        } else {
            return tourGuideRepository.findByLastname(lastname);
        }
    }

    // Thêm tour guide mới
    public TourGuide createTourGuide(TourGuide tourGuide) {
        // Kiểm tra các trường NOT NULL
        if (tourGuide.getFirstname() == null) {
            throw new IllegalArgumentException("Firstname must not be null");
        }
        if (tourGuide.getLastname() == null) {
            throw new IllegalArgumentException("Lastname must not be null");
        }
        if (tourGuide.getDateOfBirth() == null) {
            throw new IllegalArgumentException("Date of birth must not be null");
        }
        if (tourGuide.getGender() == null) {
            throw new IllegalArgumentException("Gender must not be null");
        }
        if (tourGuide.getAddress() == null) {
            throw new IllegalArgumentException("Address must not be null");
        }
        if (tourGuide.getPhoneNumber() == null) {
            throw new IllegalArgumentException("Phone number must not be null");
        }
        if (tourGuide.getCitizenId() == null) {
            throw new IllegalArgumentException("Citizen ID must not be null");
        }
        if (tourGuide.getHometown() == null) {
            throw new IllegalArgumentException("Hometown must not be null");
        }
        if (tourGuide.getSalary() == null) {
            throw new IllegalArgumentException("Salary must not be null");
        }
        if (tourGuide.getStartDate() == null) {
            throw new IllegalArgumentException("Start date must not be null");
        }
        if (tourGuide.getCardId() == null) {
            throw new IllegalArgumentException("Card ID must not be null");
        }
        if (tourGuide.getLanguage() == null) {
            throw new IllegalArgumentException("Language must not be null");
        }

        // Đảm bảo ID là null để tự động sinh
        tourGuide.setId(null);

        return tourGuideRepository.save(tourGuide);
    }

    // Cập nhật thông tin tour guide
    public TourGuide updateTourGuide(Integer id, TourGuide tourGuideDetails) {
        if (id == null) {
            throw new IllegalArgumentException("Tour guide ID must not be null");
        }
        Optional<TourGuide> tourGuideOptional = tourGuideRepository.findById(id);
        if (tourGuideOptional.isPresent()) {
            TourGuide tourGuide = tourGuideOptional.get();
            if (tourGuideDetails.getFirstname() != null) {
                tourGuide.setFirstname(tourGuideDetails.getFirstname());
            }
            if (tourGuideDetails.getLastname() != null) {
                tourGuide.setLastname(tourGuideDetails.getLastname());
            }
            if (tourGuideDetails.getDateOfBirth() != null) {
                tourGuide.setDateOfBirth(tourGuideDetails.getDateOfBirth());
            }
            if (tourGuideDetails.getGender() != null) {
                tourGuide.setGender(tourGuideDetails.getGender());
            }
            if (tourGuideDetails.getAddress() != null) {
                tourGuide.setAddress(tourGuideDetails.getAddress());
            }
            if (tourGuideDetails.getPhoneNumber() != null) {
                tourGuide.setPhoneNumber(tourGuideDetails.getPhoneNumber());
            }
            if (tourGuideDetails.getCitizenId() != null) {
                tourGuide.setCitizenId(tourGuideDetails.getCitizenId());
            }
            if (tourGuideDetails.getHometown() != null) {
                tourGuide.setHometown(tourGuideDetails.getHometown());
            }
            if (tourGuideDetails.getSalary() != null) {
                if (tourGuideDetails.getSalary().compareTo(BigDecimal.ZERO) < 0) {
                    throw new IllegalArgumentException("Salary must not be -");
                }
                tourGuide.setSalary(tourGuideDetails.getSalary());
            }
            if (tourGuideDetails.getStartDate() != null) {
                tourGuide.setStartDate(tourGuideDetails.getStartDate());
            }
            tourGuide.setEndDate(tourGuideDetails.getEndDate()); // endDate có thể null
            if (tourGuideDetails.getCardId() != null) {
                tourGuide.setCardId(tourGuideDetails.getCardId());
            }
            if (tourGuideDetails.getLanguage() != null) {
                tourGuide.setLanguage(tourGuideDetails.getLanguage());
            }
            return tourGuideRepository.save(tourGuide);
        }
        return null;
    }

    // Xóa tour guide
    public void deleteTourGuide(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Tour guide ID must not be null");
        }
        tourGuideRepository.deleteById(id);
    }
}