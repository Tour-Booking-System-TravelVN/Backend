package com.travelvn.tourbookingsytem.service;

import com.travelvn.tourbookingsytem.dto.request.FindTourRequest;
import com.travelvn.tourbookingsytem.dto.response.ApiResponse;
import com.travelvn.tourbookingsytem.dto.response.TourResponse;
import com.travelvn.tourbookingsytem.dto.response.TourUnitResponse;
import com.travelvn.tourbookingsytem.dto.response.peace.TourUnitCalendarResponse;
import com.travelvn.tourbookingsytem.exception.AppException;
import com.travelvn.tourbookingsytem.exception.ErrorCode;
import com.travelvn.tourbookingsytem.mapper.TourMapper;
import com.travelvn.tourbookingsytem.mapper.TourUnitMapper;
import com.travelvn.tourbookingsytem.mapper.peace.TourUnitCalendarMapper;
import com.travelvn.tourbookingsytem.model.*;
import com.travelvn.tourbookingsytem.repository.TourRepository;
import com.travelvn.tourbookingsytem.repository.TourUnitRepository;
import com.travelvn.tourbookingsytem.repository.TourUnitSpecification;
import com.travelvn.tourbookingsytem.repository.UserAccountRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TourUnitService {
    private final TourUnitRepository tourUnitRepository;

    private final TourMapper tourMapper;
    private final TourUnitMapper tourUnitMapper;
    private final TourUnitCalendarMapper tourUnitCalendarMapper;

    private final EntityManager entityManager;

    private final UserAccountRepository userAccountRepository;

    private static final String INFINITY = "Infinity";
    private static final Byte ITEM_OF_PAGE = (byte) 10;
    private static final Byte MAX_ITEM_OF_TYPE = (byte) 20;

//    private String keywords;
//    private LocalDate startDate;
//    private BigDecimal minPrice;
//    private BigDecimal maxPrice = BigDecimal.ZERO;

    /**
     * Tìm kiếm tour dựa trên điều kiện
     *
     * @param findTourRequest thông tin tìm kiếm
     * @return Danh sách tour phù hợp
     */
    public ApiResponse<List<TourUnitResponse>> findTours(FindTourRequest findTourRequest) {
        log.info("Tour Serive NOW");
        return switch(findTourRequest.getType()){
            case 1 -> hotTours(findTourRequest);
            case 2 -> discountedTours(findTourRequest);
            case 3 -> newTours(findTourRequest);
            default -> searchTour(findTourRequest);
        };
    }

    /**
     * Tìm kiếm tour dựa trên điều kiện nhập vào
     *
     * @param findTourRequest thông tin tìm kiếm
     * @return Danh sách tour phù hợp
     */
    public ApiResponse<List<TourUnitResponse>> searchTour(FindTourRequest findTourRequest){
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<TourUnit> query = cb.createQuery(TourUnit.class);
            Root<TourUnit> root = query.from(TourUnit.class);
            Join<TourUnit, Tour> tour = root.join("tour", JoinType.INNER);
            Join<TourUnit, Discount> discount = root.join("discount", JoinType.LEFT);
            List<Predicate> predicates = buildPredicates(cb, query, root, tour, discount, findTourRequest);

            // Subquery để tìm departure_date sớm nhất cho mỗi tour
//            Subquery<LocalDate> earliestDepartureDateSubquery = query.subquery(LocalDate.class);
//            Root<TourUnit> subRoot = earliestDepartureDateSubquery.from(TourUnit.class);
//            Join<TourUnit, Tour> subTour = subRoot.join("tour");
//            Join<TourUnit, Discount> subDiscount = subRoot.join("discount");
//
//            List<Predicate> predicates2 = buildPredicates(cb, subRoot, subTour, subDiscount, findTourRequest);
//            earliestDepartureDateSubquery.select(cb.least(subRoot.<LocalDate>get("departureDate")))
//                    .where(cb.and(predicates2.toArray(new Predicate[0])))
//                    .where(cb.equal(tour.get("tourId"), subTour.get("tourId")))
//                    .groupBy(subTour.get("tourId"));

            // Thêm điều kiện Subquery vào chính query chính
//            predicates.add(cb.in(root.get("departureDate")).value(earliestDepartureDateSubquery));


            query.select(root).distinct(false).where(cb.and(predicates.toArray(new Predicate[0])))
                    .orderBy(cb.asc(root.get("adultTourPrice")), cb.asc(root.get("departureDate")));

            var tours = entityManager.createQuery(query)
                    .setFirstResult(findTourRequest.getPage())
                    .setMaxResults(ITEM_OF_PAGE)
                    .getResultList();

            //Đảm bảo duy nhất đơn vị tour
            Iterator<TourUnit> iterator = tours.iterator();
            Set<String> tourIds = new HashSet<>();

            while (iterator.hasNext()) {
                TourUnit tourunit = iterator.next();
                if (!tourIds.add(tourunit.getTour().getTourId())) {
                    iterator.remove(); // Remove if tourId is already in the set
                }
            }

            //Đếm số tour phù hợp
            CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
            Root<TourUnit> countRoot = countQuery.from(TourUnit.class);
            Join<TourUnit, Tour> countTour = countRoot.join("tour", JoinType.INNER);
            Join<TourUnit, Discount> countDiscount = countRoot.join("discount", JoinType.LEFT);


            List<Predicate> countPredicates = buildPredicates(cb, countQuery, countRoot, countTour, countDiscount, findTourRequest);
//            countQuery.groupBy(countTour.get("tourId"));

            countQuery.select(cb.countDistinct(countTour.get("tourId"))); // Sử dụng countDistinct để khớp với distinct trong truy vấn dữ liệu
            countQuery.where(countPredicates.toArray(new Predicate[0]));

            Long totalCount = entityManager.createQuery(countQuery).getSingleResult();

            return ApiResponse.<List<TourUnitResponse>>builder()
                    .message("Có " + totalCount + " kết quả phù hợp")
                    .result(/*List<TourResponse> responseList = */tours.stream()
//                    .map(tourUnitMapper::toTourUnitResponseByFound)
                            .map(tourUnit -> {
                                TourUnitResponse res = tourUnitMapper.toTourUnitResponseByFound(tourUnit);
                                if (res.getTour() != null) {
                                    tourMapper.setFirstImageUrl(tourUnit.getTour(), res.getTour());
                                }
                                return res;
                            })
                            .collect(Collectors.toList()))
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
//        return null;
    }

    // Hàm tạo Predicate từ request
    private List<Predicate> buildPredicates(CriteriaBuilder cb, CriteriaQuery<?> query, Root<TourUnit> root, Join<TourUnit, Tour> tourJoin, Join<TourUnit, Discount> discountJoin, FindTourRequest request) {
        List<Predicate> predicates = new ArrayList<>();

        // keywords
        if (request.getKeywords() != null && !request.getKeywords().isBlank()) {
            Predicate tourNameLike = cb.like(cb.lower(tourJoin.get("tourName")), "%" + request.getKeywords().toLowerCase() + "%");
            Predicate descriptionLike = cb.like(cb.lower(tourJoin.get("description")), "%" + request.getKeywords().toLowerCase() + "%");
            predicates.add(cb.or(tourNameLike, descriptionLike));

//            this.keywords = request.getKeywords();
        }

        // departureDate
        if (request.getDepartureDate() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("departureDate"), request.getDepartureDate()));

//            this.startDate = request.getDepartureDate();
        } else {
            predicates.add(cb.greaterThanOrEqualTo(root.get("departureDate"), LocalDate.now()));

//            this.startDate = LocalDate.now();
        }

        // price
        if (request.getPrice() != null) {
            /*
            String[] priceParts = request.getPrice().split("-");
            if (priceParts.length == 2) {
                BigDecimal minPrice = new BigDecimal(priceParts[0]);

                //Giá gốc
                Path<BigDecimal> price = root.get("adultTourPrice");

                //Giá trị giảm
                Path<BigDecimal> discountValue = discountJoin.get("discountValue");

                //Đơn vị giảm
                Path<String> discountUnit = discountJoin.get("discountUnit");

                BigDecimal hundred = BigDecimal.valueOf(100);

                // Tạo biểu thức: (100 - discountValue)
                Expression<BigDecimal> discountRate = cb.diff(cb.literal(hundred), discountValue);

                // Tạo biểu thức: price * discountRate
                Expression<BigDecimal> priceTimesRate = cb.prod(price, discountRate);

                // Tạo biểu thức: (price * (100 - discountValue)) / 100
                BigDecimal oneHundredInverse = BigDecimal.ONE.divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP); // Độ chính xác và cách làm tròn tùy chọn
                Expression<BigDecimal> percentDiscount = cb.prod(priceTimesRate, cb.literal(oneHundredInverse));

                // Tạo biểu thức: (price * (100 - discountValue)) / 100
//                Expression<BigDecimal> percentDiscount = cb.quot(
//                        priceTimesRate,
//                        cb.literal(BigDecimal.valueOf(100)).as(BigDecimal.class) // ép kiểu rõ ràng ở đây
//                );

                // Tạo biểu thức: price - discountValue
                Expression<BigDecimal> fixedDiscount = cb.diff(price, discountValue);

                // Ép kiểu rõ ràng cho Case<BigDecimal>
                CriteriaBuilder.Case<BigDecimal> discountCase = cb.<BigDecimal>selectCase();

                Expression<BigDecimal> priceAfterDiscount = discountCase
                        .when(cb.equal(discountUnit, "%"), percentDiscount)
                        .when(cb.equal(discountUnit, "VND"), fixedDiscount)
                        .otherwise(price); // fallback nếu không có đơn vị


                if (priceParts[1].equalsIgnoreCase(INFINITY)) {
                    predicates.add(cb.greaterThanOrEqualTo(priceAfterDiscount, minPrice));
                    this.minPrice = new BigDecimal(priceParts[1]);
                } else {
                    BigDecimal maxPrice = new BigDecimal(priceParts[1]);
                    predicates.add(cb.between(priceAfterDiscount, minPrice, maxPrice));

                    this.minPrice = new BigDecimal(priceParts[1]);
                    this.maxPrice = maxPrice;
                }
            }
            */
             predicates.add(TourUnitSpecification.priceInRange(request.getPrice()).toPredicate(root, query, cb));
        }

//        predicates.add(cb.greaterThan(root.get("availableCapacity"), 0));
        predicates.add(TourUnitSpecification.bookingAvailable().toPredicate(root, query, cb));

        /*
        // Subquery để tìm departure_date sớm nhất cho mỗi tour
        Subquery<LocalDate> earliestDepartureDateSubquery = query.subquery(LocalDate.class);
        Root<TourUnit> subRoot = earliestDepartureDateSubquery.from(TourUnit.class);
        Join<TourUnit, Tour> subTour = subRoot.join("tour");
        Join<TourUnit, Discount> subDiscount = subRoot.join("discount");

        earliestDepartureDateSubquery.select(cb.least(subRoot.<LocalDate>get("departureDate")))
                .where(cb.and(predicates.toArray(new Predicate[0])))
                .where(cb.equal(tourJoin.get("tourId"), subTour.get("tourId")))
                .groupBy(subTour.get("tourId"));

        // Thêm điều kiện Subquery vào chính query chính
        predicates.add(cb.in(root.get("departureDate")).value(earliestDepartureDateSubquery));
        */

        return predicates;
    }

    /**
     * Danh sách tour hot
     *
     * @param findTourRequest thông tin tìm kiếm
     * @return Danh sách tour phù hợp
     */
    public ApiResponse<List<TourUnitResponse>> hotTours(FindTourRequest findTourRequest){
        try {
            List<TourUnit> tours = tourUnitRepository.hotTours();

            //Đảm bảo duy nhất đơn vị tour
            Iterator<TourUnit> iterator = tours.iterator();
            Set<String> tourIds = new HashSet<>();

            while (iterator.hasNext()) {
                TourUnit tourunit = iterator.next();
                if (!tourIds.add(tourunit.getTour().getTourId())) {
                    iterator.remove(); // Remove if tourId is already in the set
                }
            }

            int itemsQuant = MAX_ITEM_OF_TYPE < tours.size() ? MAX_ITEM_OF_TYPE : tours.size();

            return ApiResponse.<List<TourUnitResponse>>builder()
                    .message("Có " + itemsQuant + " tour hot")
                    .result(/*List<TourResponse> responseList = */tours.stream()
//                    .map(tourUnitMapper::toTourUnitResponseByFound)
                            .map(tourUnit -> {
                                TourUnitResponse res = tourUnitMapper.toTourUnitResponseByFound(tourUnit);
                                if (res.getTour() != null) {
                                    tourMapper.setFirstImageUrl(tourUnit.getTour(), res.getTour());
                                }
                                return res;
                            })
                            .collect(Collectors.toList()))
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
//        return null;
    }

    /**
     * Danh sách tour ưu đãi
     *
     * @param findTourRequest thông tin tìm kiếm
     * @return Danh sách tour phù hợp
     */
    public ApiResponse<List<TourUnitResponse>> discountedTours(FindTourRequest findTourRequest){
        try {
            List<TourUnit> tours = tourUnitRepository.discountedTours();

            //Đảm bảo duy nhất đơn vị tour
            Iterator<TourUnit> iterator = tours.iterator();
            Set<String> tourIds = new HashSet<>();

            while (iterator.hasNext()) {
                TourUnit tourunit = iterator.next();
                if (!tourIds.add(tourunit.getTour().getTourId())) {
                    iterator.remove(); // Remove if tourId is already in the set
                }
            }

            int itemsQuant = MAX_ITEM_OF_TYPE < tours.size() ? MAX_ITEM_OF_TYPE : tours.size();

            return ApiResponse.<List<TourUnitResponse>>builder()
                    .message("Có " + itemsQuant + " tour ưu đãi")
                    .result(/*List<TourResponse> responseList = */tours.stream()
//                    .map(tourUnitMapper::toTourUnitResponseByFound)
                            .map(tourUnit -> {
                                TourUnitResponse res = tourUnitMapper.toTourUnitResponseByFound(tourUnit);
                                if (res.getTour() != null) {
                                    tourMapper.setFirstImageUrl(tourUnit.getTour(), res.getTour());
                                }
                                return res;
                            })
                            .collect(Collectors.toList()))
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
//        return null;
    }

    /**
     * Danh sách tour mới
     *
     * @param findTourRequest thông tin tìm kiếm
     * @return Danh sách tour phù hợp
     */
    public ApiResponse<List<TourUnitResponse>> newTours(FindTourRequest findTourRequest){
        try {
            List<TourUnit> tours = tourUnitRepository.newTours();

            //Đảm bảo duy nhất đơn vị tour
            Iterator<TourUnit> iterator = tours.iterator();
            Set<String> tourIds = new HashSet<>();

            while (iterator.hasNext()) {
                TourUnit tourunit = iterator.next();
                if (!tourIds.add(tourunit.getTour().getTourId())) {
                    iterator.remove(); // Remove if tourId is already in the set
                }
            }

            int itemsQuant = MAX_ITEM_OF_TYPE < tours.size() ? MAX_ITEM_OF_TYPE : tours.size();

            return ApiResponse.<List<TourUnitResponse>>builder()
                    .message("Có " + itemsQuant + " tour mới")
                    .result(/*List<TourResponse> responseList = */tours.stream()
//                    .map(tourUnitMapper::toTourUnitResponseByFound)
                            .map(tourUnit -> {
                                TourUnitResponse res = tourUnitMapper.toTourUnitResponseByFound(tourUnit);
                                if (res.getTour() != null) {
                                    tourMapper.setFirstImageUrl(tourUnit.getTour(), res.getTour());
                                }
                                return res;
                            })
                            .collect(Collectors.toList()))
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
//        return null;
    }

    /**
     * Lấy danh sách các tour đã đi của mình
     * @return Danh sách các tour đã đi của mình
     */
//    public Page<TourUnitResponse> getMyTours(String status, int page){
//        var context = SecurityContextHolder.getContext();
//        String name = context.getAuthentication().getName();
//
//        UserAccount account = userAccountRepository.findById(name)
//                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
//
//        return switch (status){
//            case "done" -> getMyToursDone(account.getC().getId(), page);
//            case "opw" -> getMyToursOPW(account.getC().getId(), page);
//            default -> null;
//        };
//    }

//    public Page<TourUnitResponse> getMyToursDone(int cid, int page){
//        Pageable pageable = PageRequest.of(page, ITEM_OF_PAGE);
//        Page<TourUnit> rs = tourUnitRepository.getMyToursDone(cid, pageable);
//
//        // Dùng map() với stream để chuyển đổi đối tượng TourUnit sang TourUnitResponse
//        List<TourUnitResponse> responseList = rs.stream()
//                .map(tourUnitMapper::toTourUnitResponseByFound)
//                .collect(Collectors.toList());
//
//        // Trả về Page<TourUnitResponse> mới, với dữ liệu đã chuyển đổi
//        return new PageImpl<>(responseList, pageable, rs.getTotalElements());
//    }
//
//    public Page<TourUnitResponse> getMyToursOPW(int cid, int page){
//        Pageable pageable = PageRequest.of(page, ITEM_OF_PAGE);
//        Page<TourUnit> rs = tourUnitRepository.getMyToursOPW(cid, pageable);
//
//        // Dùng map() với stream để chuyển đổi đối tượng TourUnit sang TourUnitResponse
//        List<TourUnitResponse> responseList = rs.stream()
//                .map(tourUnitMapper::toTourUnitResponseByFound)
//                .collect(Collectors.toList());
//
//        // Trả về Page<TourUnitResponse> mới, với dữ liệu đã chuyển đổi
//        return new PageImpl<>(responseList, pageable, rs.getTotalElements());
//    }

    /**
     * Lấy các đơn vị tour theo tháng và năm
     * @param month tháng
     * @param year năm
     * @return Danh sách các đơn vị tour theo tháng và năm
     */
    public List<TourUnitCalendarResponse> getTourUnitCalendar(int month, int year, String tourId){
        List<TourUnit> tourUnits = tourUnitRepository.getTourUnitCalendar(month, year, tourId);
        return tourUnits.stream().map(tourUnitCalendarMapper::tourUnitCalendarResponse).collect(Collectors.toList());
    }
}
