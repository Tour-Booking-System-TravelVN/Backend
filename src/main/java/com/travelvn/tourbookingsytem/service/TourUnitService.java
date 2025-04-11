package com.travelvn.tourbookingsytem.service;

import com.travelvn.tourbookingsytem.dto.request.FindTourRequest;
import com.travelvn.tourbookingsytem.dto.response.ApiResponse;
import com.travelvn.tourbookingsytem.dto.response.TourResponse;
import com.travelvn.tourbookingsytem.dto.response.TourUnitResponse;
import com.travelvn.tourbookingsytem.mapper.TourMapper;
import com.travelvn.tourbookingsytem.mapper.TourUnitMapper;
import com.travelvn.tourbookingsytem.model.Booking;
import com.travelvn.tourbookingsytem.model.Discount;
import com.travelvn.tourbookingsytem.model.Tour;
import com.travelvn.tourbookingsytem.model.TourUnit;
import com.travelvn.tourbookingsytem.repository.TourUnitRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TourUnitService {
    private final TourUnitRepository tourRepository;
    private final TourMapper tourMapper;
    private final TourUnitMapper tourUnitMapper;
    private final EntityManager entityManager;

    private static final String INFINITY = "Infinity";
    private static final Byte ITEM_OF_PAGE = (byte) 10;
    private final TourUnitRepository tourUnitRepository;

    private String keywords;
    private LocalDate startDate;
    private BigDecimal minPrice;
    private BigDecimal maxPrice = BigDecimal.ZERO;

    /**
     * Tìm kiếm tour dựa trên điều kiện nhập vào
     *
     * @param findTourRequest thông tin tìm kiếm
     * @return Danh sách tour phù hợp
     */
    public ApiResponse<List<TourUnitResponse>> findTours(FindTourRequest findTourRequest) {
        log.info("Tour Serive NOW");
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


            query.select(root).distinct(false).where(cb.and(predicates.toArray(new Predicate[0])));
            var tours = entityManager.createQuery(query)
                    .setFirstResult(findTourRequest.getPage())
                    .setMaxResults(ITEM_OF_PAGE)
                    .getResultList();


            //keywords
            //Tìm từ khóa trong mô tả hoặc tên tour
//            if (findTourRequest.getKeywords() != null) {
//                Predicate tourNameLike = cb.like(cb.lower(tour.get("tourName")), "%" + findTourRequest.getKeywords().toLowerCase() + "%");
//                Predicate descriptionLike = cb.like(cb.lower(tour.get("description")), "%" + findTourRequest.getKeywords().toLowerCase() + "%");
//                Predicate tourNameOrDescriptionLike = cb.or(tourNameLike, descriptionLike);
//                predicates.add(tourNameOrDescriptionLike);
//
//            }

            //Tìm tour có thời gian bắt đầu sau ngày nhập vào nếu không có thì sau ngày hiện tại
//            if (findTourRequest.getDepartureDate() != null) {
//                predicates.add(cb.greaterThanOrEqualTo(root.get("departureDate"), findTourRequest.getDepartureDate()));
//            } else {
//                predicates.add(cb.greaterThanOrEqualTo(root.get("departureDate"), LocalDate.now()));
//            }

            //Tìm theo giá
//            if (findTourRequest.getPrice() != null) {
//                //Lấy giá nhỏ nhất
//                BigDecimal minPrice = new BigDecimal(findTourRequest.getPrice().split("-")[0]);
//
//                String temp = findTourRequest.getPrice().split("-")[1];
//                if (temp.equalsIgnoreCase(INFINITY)) {
//                    predicates.add(cb.greaterThanOrEqualTo(root.get("adultTourPrice"), minPrice));
//                } else {
//                    BigDecimal maxPrice = new BigDecimal(findTourRequest.getPrice().split("-")[1]);
//                    predicates.add(cb.between(root.get("adultTourPrice"), minPrice, maxPrice));
//                }
//            }


//            earliestDepartureDateSubquery.select(cb.least(subRoot.<LocalDate>get("departureDate")))
//                    .where(cb.equal(tour.get("tourId"), subTour.get("tourId")));

            // Chọn ngày khởi hành sớm nhất cho mỗi tour
//            earliestDepartureDateSubquery.select(cb.least(subRoot.<LocalDate>get("departureDate")))
//                    .groupBy(subTour.get("tourId")) // Thêm groupBy để đảm bảo lấy ngày khởi hành sớm nhất cho mỗi tour
//                    .where(cb.equal(subTour.get("tourId"), tour.get("tourId"))); // Kết nối tourId

            //Điều kiện trong subQuery


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

//            List<TourUnit> tours;
//            if(!Objects.equals(this.maxPrice, BigDecimal.ZERO))
//                tours = tourUnitRepository.findEarliestDepartureTourUnitsNative(keywords, startDate, minPrice, maxPrice);
//            else
//                tours = tourUnitRepository.findEarliestDepartureTourUnitsNative2(keywords, startDate, minPrice);

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

//            return ApiResponse.<List<TourUnitResponse>>builder()
//                    .message("Có " + totalCount + " kết quả phù hợp")
//                    .result(/*List<TourResponse> responseList = */tours.stream()
////                    .map(tourUnitMapper::toTourUnitResponseByFound)
//                            .map(tourUnit -> {
//                                TourUnitResponse res = tourUnitMapper.toTourUnitResponseByFound(tourUnit);
//                                if (res.getTour() != null) {
//                                    tourMapper.setFirstImageUrl(tourUnit.getTour(), res.getTour());
//                                }
//                                return res;
//                            })
//                            .collect(Collectors.toList()))
//                    .build();

//            return /*List<TourResponse> responseList = */tours.stream()
////                    .map(tourUnitMapper::toTourUnitResponseByFound)
//                    .map(tourUnit -> {
//                        TourUnitResponse res = tourUnitMapper.toTourUnitResponseByFound(tourUnit);
//                        if (res.getTour() != null) {
//                            tourMapper.setFirstImageUrl(tourUnit.getTour(), res.getTour());
//                        }
//                        return res;
//                    })
//                    .collect(Collectors.toList());
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

            this.keywords = request.getKeywords();
        }

        // departureDate
        if (request.getDepartureDate() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("departureDate"), request.getDepartureDate()));

            this.startDate = request.getDepartureDate();
        } else {
            predicates.add(cb.greaterThanOrEqualTo(root.get("departureDate"), LocalDate.now()));

            this.startDate = LocalDate.now();
        }

        // price
        if (request.getPrice() != null) {
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
        }

        predicates.add(cb.greaterThan(root.get("availableCapacity"), 0));


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


        return predicates;
    }

}
