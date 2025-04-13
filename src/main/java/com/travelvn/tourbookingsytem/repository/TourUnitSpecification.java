package com.travelvn.tourbookingsytem.repository;

import com.travelvn.tourbookingsytem.model.Discount;
import com.travelvn.tourbookingsytem.model.TourUnit;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class TourUnitSpecification {

    /**
     * Tìm khách hàng theo tên
     * (Test CriteriaBuilder)
     *
     * @return Danh sách các khách hàng có tên phù hợp
     */
    public static Specification<TourUnit> bookingAvailable() {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get("availableCapacity"), 0);
    }

    /**
     * Điều kiện giá trong khoảng
     *
     * @param priceRange
     * @return
     */
    public static Specification<TourUnit> priceInRange(String priceRange) {
        return (root, query, cb) -> {
            String[] priceParts = priceRange.split("-");
            if (priceParts.length != 2) return null;

            BigDecimal minPrice = new BigDecimal(priceParts[0]);

            // Join bảng Discount (nếu là quan hệ @OneToOne hoặc @ManyToOne)
            Join<TourUnit, Discount> discountJoin = root.join("discount", JoinType.LEFT);

            Path<BigDecimal> price = root.get("adultTourPrice");
            Path<BigDecimal> discountValue = discountJoin.get("discountValue");
            Path<String> discountUnit = discountJoin.get("discountUnit");

            BigDecimal hundred = BigDecimal.valueOf(100);
            Expression<BigDecimal> discountRate = cb.diff(cb.literal(hundred), discountValue);
            Expression<BigDecimal> priceTimesRate = cb.prod(price, discountRate);

            // (price * (100 - discountValue)) / 100
            Expression<BigDecimal> percentDiscount = cb.prod(
                    priceTimesRate,
                    cb.literal(BigDecimal.ONE.divide(hundred, 10, RoundingMode.HALF_UP))
            );

            // price - discountValue
            Expression<BigDecimal> fixedDiscount = cb.diff(price, discountValue);

            // Ép kiểu rõ ràng cho Case<BigDecimal>
            CriteriaBuilder.Case<BigDecimal> discountCase = cb.<BigDecimal>selectCase();

            Expression<BigDecimal> priceAfterDiscount = discountCase
                    .when(cb.equal(discountUnit, "%"), percentDiscount)
                    .when(cb.equal(discountUnit, "VND"), fixedDiscount)
                    .otherwise(price); // fallback nếu không có đơn vị

            // Điều kiện giá
            if (priceParts[1].equalsIgnoreCase("INFINITY")) {
                return cb.greaterThanOrEqualTo(priceAfterDiscount, minPrice);
            } else {
                BigDecimal maxPrice = new BigDecimal(priceParts[1]);
                return cb.between(priceAfterDiscount, minPrice, maxPrice);
            }
        };
    }

}
