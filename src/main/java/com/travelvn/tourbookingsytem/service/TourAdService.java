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
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @Autowired
    private CloudinaryService cloudinaryService;

    public List<TourAdResponse> getAllTours() {
        logger.info("Lấy tất cả tour");
        List<Tour> tours = tourRepository.findAll();
        logger.debug("Tìm thấy {} tour", tours.size());
        return tours.stream().map(tourMapper::toResponse).collect(Collectors.toList());
    }

    public TourAdResponse getTourById(String id) {
        if (id == null || id.trim().isEmpty()) {
            logger.error("ID tour không được để trống hoặc null");
            throw new IllegalArgumentException("ID tour không được để trống hoặc null");
        }

        logger.info("Lấy tour với ID: {}", id);
        Optional<Tour> tour = tourRepository.findByTourId(id);
        if (tour.isPresent()) {
            logger.debug("Tìm thấy tour: {}", tour.get());
            return tourMapper.toResponse(tour.get());
        } else {
            logger.warn("Không tìm thấy tour với ID {}", id);
            throw new RuntimeException("Không tìm thấy tour với ID " + id);
        }
    }

    public Optional<Tour> searchTourById(String tourId) {
        logger.info("Tìm kiếm tour theo ID: {}", tourId);
        return tourRepository.findByTourId(tourId);
    }

    public Optional<Tour> findById(String id) {
        logger.info("Tìm kiếm tour theo ID: {}", id);
        return tourRepository.findById(id);
    }

    public List<Tour> searchByNameOrLocation(String keyword) {
        logger.info("Tìm kiếm tour theo từ khóa (tên/địa điểm): {}", keyword);
        return tourRepository.searchByNameOrPlace(keyword);
    }

    public List<TourAdResponse> searchToursByLocationOrName(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            logger.warn("Từ khóa tìm kiếm không được để trống hoặc null");
            throw new IllegalArgumentException("Từ khóa tìm kiếm không được để trống hoặc null");
        }

        logger.info("Tìm kiếm tour với từ khóa: {}", keyword);
        List<Tour> tours = tourRepository.findByLocationOrTourNameContaining(keyword.trim());
        logger.debug("Tìm thấy {} tour với từ khóa '{}'", tours.size(), keyword);
        return tours.stream().map(tourMapper::toResponse).collect(Collectors.toList());
    }

    @Transactional
    public TourAdResponse createTour(TourAdRequest tourAdRequest) {
        logger.info("Bắt đầu tạo tour từ yêu cầu: {}", tourAdRequest);
        Tour tour = tourMapper.toEntity(tourAdRequest);

        // Lấy các thực thể được quản lý
        if (tourAdRequest.getTourOperatorId() != null) {
            TourOperator operator = tourOperatorRepository.findById(tourAdRequest.getTourOperatorId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy TourOperator"));
            tour.setTourOperator(operator);
        }

        if (tourAdRequest.getLastUpdatedOperator() != null) {
            TourOperator updater = tourOperatorRepository.findById(tourAdRequest.getLastUpdatedOperator())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy LastUpdatedOperator"));
            tour.setLastUpdatedOperator(updater);
        }

        if (tourAdRequest.getCategoryId() != null) {
            Category category = categoryRepository.findById(tourAdRequest.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy Category"));
            tour.setCategory(category);
        }

        // Xử lý tải lên hình ảnh
        Map<String, String> imageMap = new HashMap<>();
        if (tourAdRequest.getImages() != null && !tourAdRequest.getImages().isEmpty()) {
            for (MultipartFile imageFile : tourAdRequest.getImages()) {
                Map<String, String> uploadResult = cloudinaryService.uploadFile(imageFile, "tour-booking-system/tours");
                imageMap.put(uploadResult.get("publicId"), uploadResult.get("url"));
            }
        }
        tour.setImageMap(imageMap);

        // Tạo ID tour duy nhất và lưu
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
                    // Xóa các ảnh đã tải lên nếu không thể lưu tour
                    for (String publicId : imageMap.keySet()) {
                        cloudinaryService.deleteFile(publicId);
                    }
                    throw new RuntimeException("Không thể tạo tourId duy nhất");
                }
            }
        }

        // Xóa ảnh nếu có lỗi bất ngờ
        for (String publicId : imageMap.keySet()) {
            cloudinaryService.deleteFile(publicId);
        }
        throw new IllegalStateException("Lỗi bất ngờ trong quá trình tạo tour");
    }

    @Transactional
    public TourAdResponse updateTour(String id, TourAdRequest tourAdRequest) {
        if (id == null || id.trim().isEmpty()) {
            logger.error("ID tour không được để trống hoặc null");
            throw new IllegalArgumentException("ID tour không được để trống hoặc null");
        }

        Optional<Tour> tourOptional = tourRepository.findByTourId(id);
        if (!tourOptional.isPresent()) {
            logger.warn("Không tìm thấy tour với ID {}", id);
            throw new RuntimeException("Không tìm thấy tour với ID " + id);
        }

        Tour tour = tourOptional.get();
        tourMapper.updateEntityFromRequest(tourAdRequest, tour);

        // Xử lý cập nhật hình ảnh
        if (tourAdRequest.getImages() != null && !tourAdRequest.getImages().isEmpty()) {
            // Xóa các ảnh cũ từ Cloudinary
            for (String publicId : tour.getImageMap().keySet()) {
                cloudinaryService.deleteFile(publicId);
            }
            tour.getImageMap().clear();

            // Tải lên các ảnh mới
            Map<String, String> imageMap = new HashMap<>();
            for (MultipartFile imageFile : tourAdRequest.getImages()) {
                Map<String, String> uploadResult = cloudinaryService.uploadFile(imageFile, "tour-booking-system/tours");
                imageMap.put(uploadResult.get("publicId"), uploadResult.get("url"));
            }
            tour.setImageMap(imageMap);
        }

        Tour updatedTour = tourRepository.save(tour);
        return tourMapper.toResponse(updatedTour);
    }

    @Transactional
    public void deleteTour(String id) {
        if (id == null || id.trim().isEmpty()) {
            logger.error("ID tour không được để trống hoặc null");
            throw new IllegalArgumentException("ID tour không được để trống hoặc null");
        }

        logger.info("Xóa tour với ID: {}", id);
        Optional<Tour> tourOptional = tourRepository.findByTourId(id);
        if (!tourOptional.isPresent()) {
            logger.warn("Không tìm thấy tour với ID {}", id);
            throw new RuntimeException("Không tìm thấy tour với ID " + id);
        }

        Tour tour = tourOptional.get();
        // Xóa các ảnh từ Cloudinary
        for (String publicId : tour.getImageMap().keySet()) {
            cloudinaryService.deleteFile(publicId);
        }

        try {
            tourRepository.deleteById(id);
            logger.info("Xóa tour thành công với ID: {}", id);
        } catch (Exception e) {
            logger.error("Xóa tour thất bại với ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Xóa tour thất bại: " + e.getMessage(), e);
        }
    }
}