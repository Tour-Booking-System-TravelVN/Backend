package com.travelvn.tourbookingsytem.model;

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

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tour_unit_id", nullable = false)
    private TourUnit tourUnit;

    @Column(name = "booking_date", nullable = false)
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

    @Column(name = "expired_at")
    private Long expiredAt;

    @Column(name = "private_room_number", nullable = false)
    private Byte privateRoomNumber;

    @ToString.Exclude
    @OneToMany(mappedBy = "booking", cascade = {CascadeType.REMOVE})
    private Set<CompanionCustomer> companionCustomerSet = new HashSet<>();
}