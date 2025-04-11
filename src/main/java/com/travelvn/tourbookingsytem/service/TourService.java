package com.travelvn.tourbookingsytem.service;

import com.travelvn.tourbookingsytem.dto.request.FindTourRequest;
import com.travelvn.tourbookingsytem.dto.response.ApiResponse;
import com.travelvn.tourbookingsytem.dto.response.TourResponse;
import com.travelvn.tourbookingsytem.mapper.TourMapper;
import com.travelvn.tourbookingsytem.model.Tour;
import com.travelvn.tourbookingsytem.model.TourUnit;
import com.travelvn.tourbookingsytem.repository.TourRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TourService {
    private final TourRepository tourRepository;
    private final TourMapper tourMapper;
    private final EntityManager entityManager;

    private static final String INFINITY = "Infinity";
    private static final Byte ITEM_OF_PAGE = (byte)10;

    /**
     * Tìm kiếm tour dựa trên điều kiện nhập vào
     * @param findTourRequest thông tin tìm kiếm
     * @return Danh sách tour phù hợp
     */
    public List<TourResponse> findTours(FindTourRequest findTourRequest) {
        log.info("Tour Serive NOW");
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Tour> query = cb.createQuery(Tour.class);
            Root<Tour> root = query.from(Tour.class);
            Join<Tour, TourUnit> tourUnit = root.join("tourUnitSet", JoinType.INNER);

            List<Predicate> predicates = new ArrayList<>();

            //keywords
            //Tìm từ khóa trong mô tả hoặc tên tour
            if (findTourRequest.getKeywords() != null) {
                Predicate tourNameLike = cb.like(cb.lower(root.get("tourName")), "%" + findTourRequest.getKeywords().toLowerCase() + "%");
                Predicate descriptionLike = cb.like(cb.lower(root.get("description")), "%" + findTourRequest.getKeywords().toLowerCase() + "%");
                Predicate tourNameOrDescriptionLike = cb.or(tourNameLike, descriptionLike);
                predicates.add(tourNameOrDescriptionLike);

            }

            //Tìm tour có thời gian bắt đầu sau ngày nhập vào nếu không có thì sau ngày hiện tại
            if (findTourRequest.getDepartureDate() != null) {
                predicates.add(cb.greaterThanOrEqualTo(tourUnit.get("departureDate"), findTourRequest.getDepartureDate()));
            } else {
                predicates.add(cb.greaterThanOrEqualTo(tourUnit.get("departureDate"), LocalDate.now()));
            }

            //Tìm theo giá
            if (findTourRequest.getPrice() != null) {
                //Lấy giá nhỏ nhất
                BigDecimal minPrice = new BigDecimal(findTourRequest.getPrice().split("-")[0]);

                String temp = findTourRequest.getPrice().split("-")[1];
                if (temp.equalsIgnoreCase(INFINITY)) {
                    predicates.add(cb.greaterThanOrEqualTo(tourUnit.get("adultTourPrice"), minPrice));
                } else {
                    BigDecimal maxPrice = new BigDecimal(findTourRequest.getPrice().split("-")[1]);
                    predicates.add(cb.between(tourUnit.get("adultTourPrice"), minPrice, maxPrice));
                }
            }

            query.select(root).distinct(true).where(predicates.toArray(new Predicate[0]));

            var tours = entityManager.createQuery(query)
                    .setFirstResult(findTourRequest.getPage())
                    .setMaxResults(ITEM_OF_PAGE)
                    .getResultList();

            return /*List<TourResponse> responseList = */tours.stream()
                    .map(tourMapper::toTourResponseByFound)
                    .collect(Collectors.toList());
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
//        return null;
    }

}
