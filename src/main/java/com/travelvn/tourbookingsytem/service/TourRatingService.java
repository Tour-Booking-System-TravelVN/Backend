package com.travelvn.tourbookingsytem.service;

import com.travelvn.tourbookingsytem.dto.request.TourRatingRequest;
import com.travelvn.tourbookingsytem.dto.response.TourRatingResponse;
import com.travelvn.tourbookingsytem.enums.BookingStatus;
import com.travelvn.tourbookingsytem.exception.AppException;
import com.travelvn.tourbookingsytem.exception.ErrorCode;
import com.travelvn.tourbookingsytem.mapper.TourRatingMapper;
import com.travelvn.tourbookingsytem.model.Booking;
import com.travelvn.tourbookingsytem.model.TourRating;
import com.travelvn.tourbookingsytem.model.TourUnit;
import com.travelvn.tourbookingsytem.model.UserAccount;
import com.travelvn.tourbookingsytem.repository.BookingRepository;
import com.travelvn.tourbookingsytem.repository.TourRatingRepository;
import com.travelvn.tourbookingsytem.repository.TourUnitRepository;
import com.travelvn.tourbookingsytem.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class TourRatingService {
    private final TourRatingRepository tourRatingRepository;
    private final TourRatingMapper tourRatingMapper;

    private final TourUnitRepository tourUnitRepository;
    private final BookingRepository bookingRepository;
    private final UserAccountRepository userAccountRepository;

    private static final String APPROVE = "đã duyệt";
    private static final String WAIT = "chờ duyệt";
    /**
     * Lấy các đánh giá của tour theo mã tour
     * @param tourId
     * @return
     */
    public List<TourRatingResponse> getTourRatingByTour(String tourId) {
        List<TourRating> tourRatings = tourRatingRepository.findTop10ByStatusContainingIgnoreCaseAndTourUnit_Tour_TourIdIgnoreCaseOrderByRatingValueDesc(APPROVE, tourId);
        return tourRatings.stream().map(tourRatingMapper::toTourRatingResponse).collect(Collectors.toList());
    }

    /**
     * Tạo đánh giá tour
     * @param tourRatingRequest Đánh giá tour
     * @return Kết quả tạo
     */
    public boolean createTourRating(TourRatingRequest tourRatingRequest) {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        UserAccount account = userAccountRepository.findById(name)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        //Kiểm tra đơn vị tour có tồn tại
        TourUnit tourUnit = tourUnitRepository.findById(tourRatingRequest.getTourUnitId())
                .orElseThrow(() -> new AppException(ErrorCode.TOURUNIT_NOT_EXISTED));

        //Kiểm tra người này đã đi tour này
        Booking booking = bookingRepository.findBookingByC_IdEqualsAndTourUnit_TourUnitIdEqualsIgnoreCaseAndStatusEqualsIgnoreCase(account.getC().getId(), tourRatingRequest.getTourUnitId(), BookingStatus.D.name())
                .orElseThrow(() -> new AppException(ErrorCode.BOOKING_NOT_DONE));

        //Kiểm tra xem đã đánh giá tour chưa
        TourRating tourRating = tourRatingRepository.findTourRatingByC_IdEqualsAndTourUnit_TourUnitIdEqualsIgnoreCase(account.getC().getId(), tourRatingRequest.getTourUnitId()).orElse(null);
        if (tourRating != null)
            throw new AppException(ErrorCode.TOUR_RATING_EXISTED);

        //Map
        tourRating = tourRatingMapper.toTourRating(tourRatingRequest);
        log.info(tourRating.toString());
        tourRating.setStatus(WAIT);
        tourRating.setC(account.getC());
        tourRating.setTourUnit(tourUnit);
        tourRatingRepository.save(tourRating);

        return true;
    }

    /**
     * Kiểm tra xem đã đánh giá tour chưa
     * @return Kết quả đã đánh giá tour chưa
     * true: chưa
     * false: rồi
     */
    public boolean checkTourRating(String tourUnitId) {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        UserAccount account = userAccountRepository.findById(name)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        //Kiểm tra đơn vị tour có tồn tại
        TourUnit tourUnit = tourUnitRepository.findById(tourUnitId)
                .orElseThrow(() -> new AppException(ErrorCode.TOURUNIT_NOT_EXISTED));

        //Kiểm tra người này đã đi tour này
        Booking booking = bookingRepository.findBookingByC_IdEqualsAndTourUnit_TourUnitIdEqualsIgnoreCaseAndStatusEqualsIgnoreCase(account.getC().getId(), tourUnitId, BookingStatus.D.name())
                .orElseThrow(() -> new AppException(ErrorCode.BOOKING_NOT_DONE));

        //Kiểm tra xem đã đánh giá tour chưa
        TourRating tourRating = tourRatingRepository.findTourRatingByC_IdEqualsAndTourUnit_TourUnitIdEqualsIgnoreCase(account.getC().getId(), tourUnitId).orElse(null);
        return (tourRating == null);
    }
}
