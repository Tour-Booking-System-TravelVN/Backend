package com.travelvn.tourbookingsytem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.travelvn.tourbookingsytem.model.generator.BookingIdGenerator;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "booking")
public class Booking {
    @Id
    @Column(name = "booking_id", nullable = false, length = 10)
    @GeneratedValue(generator = "booking_id")
    @GenericGenerator(name = "booking_id", type = BookingIdGenerator.class)
    private String bookingId;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "c_id", nullable = false)
    private Customer c;

    @JsonProperty("cId")
    @Transient
    public Integer getCId() {
        return c != null ? c.getId() : null;
    }

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_unit_id")
    private TourUnit tourUnit;

    @JsonProperty("tourUnitId")
    @Transient
    public String getTourUnitId() {
        return tourUnit != null ? tourUnit.getTourUnitId() : null;
    }

    @Column(name = "booking_date")
    private Instant bookingDate;

    @Column(name = "baby_number", nullable = false)
    private Byte babyNumber;

    @Column(name = "toddler_number", nullable = false)
    private Byte toddlerNumber;

    @Column(name = "child_number", nullable = false)
    private Byte childNumber;

    @Column(name = "adult_number", nullable = false)
    private Byte adultNumber;

    @Column(name = "status", nullable = false, length = 1)
    private String status;

    @Lob
    @Column(name = "note")
    private String note;

    @Column(name = "payment_id")
    private String paymentId;

    @Column(name = "total_amount", nullable = false, precision = 19, scale = 3)
    private BigDecimal totalAmount;

    @Column(name = "order_code")
    private Long orderCode;

    /**
     * Thời điểm hệ thống nhận được yêu cầu hủy tour.
     * Trước đây dùng để lưu thời gian hết hạn thanh toán (không còn sử dụng).
     */
    @Column(name = "expired_at")
    private Long expiredAt;

    @Column(name = "private_room_number", nullable = false)
    private Byte privateRoomNumber;

    @ToString.Exclude
    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CompanionCustomer> companionCustomerSet = new HashSet<>();
}