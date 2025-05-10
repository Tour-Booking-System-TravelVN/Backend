package com.travelvn.tourbookingsytem.service;

import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.DocumentReference;
import com.travelvn.tourbookingsytem.dto.request.TourUnitRequest;
import com.travelvn.tourbookingsytem.dto.response.TourUnitResponse;
import com.travelvn.tourbookingsytem.mapper.TourUnitMapper;
import com.travelvn.tourbookingsytem.model.Discount;
import com.travelvn.tourbookingsytem.model.Festival;
import com.travelvn.tourbookingsytem.model.Tour;
import com.travelvn.tourbookingsytem.model.TourOperator;
import com.travelvn.tourbookingsytem.model.TourUnit;
import com.travelvn.tourbookingsytem.repository.DiscountRepository;
import com.travelvn.tourbookingsytem.repository.FestivalRepository;
import com.travelvn.tourbookingsytem.repository.TourOperatorRepository;
import com.travelvn.tourbookingsytem.repository.TourRepository;
import com.travelvn.tourbookingsytem.repository.TourUnitRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TourUnitService {

    @Autowired
    private TourUnitRepository tourUnitRepository;

    @Autowired
    private TourRepository tourRepository;

    @Autowired
    private FestivalRepository festivalRepository;

    @Autowired
    private DiscountRepository discountRepository;

    @Autowired
    private TourOperatorRepository tourOperatorRepository;

    @Autowired
    private TourUnitMapper tourUnitMapper;

    @Autowired
    private Firestore firestore; // Inject Firestore thay vì FirebaseDatabase

    @Transactional(readOnly = true)
    public List<TourUnitResponse> getAllTourUnits() {
        return tourUnitRepository.findAll().stream()
                .map(tourUnitMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TourUnitResponse getTourUnitById(String tourUnitId) {
        return tourUnitRepository.findById(tourUnitId)
                .map(tourUnitMapper::toResponse)
                .orElse(null);
    }

    @Transactional
    public TourUnitResponse createTourUnit(TourUnitRequest tourUnitRequest) {
        // Kiểm tra và lấy các thực thể liên quan
        Tour tour = tourRepository.findById(tourUnitRequest.getTourId())
                .orElseThrow(() -> new RuntimeException("Tour với ID " + tourUnitRequest.getTourId() + " không tìm thấy"));
        Festival festival = festivalRepository.findById(tourUnitRequest.getFestivalId())
                .orElseThrow(() -> new RuntimeException("Festival với ID " + tourUnitRequest.getFestivalId() + " không tìm thấy"));
        TourOperator tourOperator = tourOperatorRepository.findById(tourUnitRequest.getTourOperatorId())
                .orElseThrow(() -> new RuntimeException("Nhà điều hành tour với ID " + tourUnitRequest.getTourOperatorId() + " không tìm thấy"));
        TourOperator lastUpdatedOperator = tourUnitRequest.getLastUpdatedOperatorId() != null ?
                tourOperatorRepository.findById(tourUnitRequest.getLastUpdatedOperatorId())
                        .orElseThrow(() -> new RuntimeException("Nhà điều hành cập nhật cuối với ID " + tourUnitRequest.getLastUpdatedOperatorId() + " không tìm thấy")) : null;
        Discount discount = tourUnitRequest.getDiscountId() != null ?
                discountRepository.findById(tourUnitRequest.getDiscountId())
                        .orElseThrow(() -> new RuntimeException("Ưu đãi với ID " + tourUnitRequest.getDiscountId() + " không tìm thấy")) : null;

        // Tạo TourUnit từ request
        TourUnit tourUnit = tourUnitMapper.toEntity(tourUnitRequest);
        tourUnit.setTour(tour);
        tourUnit.setFestival(festival);
        tourUnit.setTourOperator(tourOperator);
        tourUnit.setLastUpdatedOperator(lastUpdatedOperator);
        tourUnit.setDiscount(discount);

        // Sinh tourUnitId nếu chưa có
        if (tourUnit.getTourUnitId() == null || tourUnit.getTourUnitId().isEmpty()) {
            String newTourUnitId = generateNextTourUnitId(tourUnitRequest.getTourId());
            tourUnit.setTourUnitId(newTourUnitId);
        }
        tourUnit.setCreatedTime(Instant.now());
        tourUnit.setLastUpdatedTime(Instant.now());

        // Lưu TourUnit
        TourUnit savedTourUnit = tourUnitRepository.save(tourUnit);

        // Tạo nhóm chat trong Firestore
        createChatGroup(savedTourUnit.getTourUnitId(), tour.getTourId());

        return tourUnitMapper.toResponse(savedTourUnit);
    }

    @Transactional
    public TourUnitResponse updateTourUnit(String tourUnitId, TourUnitRequest tourUnitRequest) {
        return tourUnitRepository.findById(tourUnitId).map(tourUnit -> {
            // Kiểm tra và lấy các thực thể liên quan
            Tour tour = tourRepository.findById(tourUnitRequest.getTourId())
                    .orElseThrow(() -> new RuntimeException("Tour với ID " + tourUnitRequest.getTourId() + " không tìm thấy"));
            Festival festival = festivalRepository.findById(tourUnitRequest.getFestivalId())
                    .orElseThrow(() -> new RuntimeException("Festival với ID " + tourUnitRequest.getFestivalId() + " không tìm thấy"));
            TourOperator tourOperator = tourOperatorRepository.findById(tourUnitRequest.getTourOperatorId())
                    .orElseThrow(() -> new RuntimeException("Nhà điều hành tour với ID " + tourUnitRequest.getTourOperatorId() + " không tìm thấy"));
            TourOperator lastUpdatedOperator = tourUnitRequest.getLastUpdatedOperatorId() != null ?
                    tourOperatorRepository.findById(tourUnitRequest.getLastUpdatedOperatorId())
                            .orElseThrow(() -> new RuntimeException("Nhà điều hành cập nhật cuối với ID " + tourUnitRequest.getLastUpdatedOperatorId() + " không tìm thấy")) : null;
            Discount discount = tourUnitRequest.getDiscountId() != null ?
                    discountRepository.findById(tourUnitRequest.getDiscountId())
                            .orElseThrow(() -> new RuntimeException("Ưu đãi với ID " + tourUnitRequest.getDiscountId() + " không tìm thấy")) : null;

            // Cập nhật TourUnit
            tourUnitMapper.updateEntityFromRequest(tourUnitRequest, tourUnit);
            tourUnit.setTour(tour);
            tourUnit.setFestival(festival);
            tourUnit.setTourOperator(tourOperator);
            tourUnit.setLastUpdatedOperator(lastUpdatedOperator);
            tourUnit.setDiscount(discount);
            tourUnit.setLastUpdatedTime(Instant.now());

            TourUnit updatedTourUnit = tourUnitRepository.save(tourUnit);
            return tourUnitMapper.toResponse(updatedTourUnit);
        }).orElse(null);
    }

    @Transactional
    public void deleteTourUnit(String tourUnitId) {
        tourUnitRepository.findById(tourUnitId).ifPresent(tourUnitRepository::delete);
    }

    private String generateNextTourUnitId(String tourId) {
        String maxTourUnitId = tourUnitRepository.findMaxTourUnitIdByTourIdPrefix(tourId).orElse(null);
        int nextNumber = 0;
        if (maxTourUnitId != null) {
            String numberPart = maxTourUnitId.substring(maxTourUnitId.lastIndexOf('-') + 1);
            try {
                nextNumber = Integer.parseInt(numberPart) + 1;
            } catch (NumberFormatException e) {
                nextNumber = 0;
            }
        }
        String formattedNumber = String.format("%06d", nextNumber);
        return tourId + "-" + formattedNumber;
    }

    private void createChatGroup(String tourUnitId, String tourId) {
        try {
            // Tham chiếu đến collection chat_groups trong Firestore
            DocumentReference chatGroupRef = firestore.collection("chats").document(tourUnitId);

            // Tạo dữ liệu cho nhóm chat
            Map<String, Object> chatGroup = new HashMap<>();
            chatGroup.put("groupName", "Nhóm chat cho TourUnit " + tourUnitId + " - Tour " + tourId);
            chatGroup.put("tourUnitId", tourUnitId);

            // Lưu nhóm chat vào Firestore (đồng bộ để dễ debug)
            chatGroupRef.set(chatGroup);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi tạo nhóm chat cho TourUnit " + tourUnitId, e);
        }
    }

    // Không cần lớp ChatGroup nữa vì Firestore dùng Map
}