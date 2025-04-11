package com.travelvn.tourbookingsytem.service;

import com.travelvn.tourbookingsytem.dto.request.BookingRequest;
import com.travelvn.tourbookingsytem.dto.request.CompanionCustomerRequest;
import com.travelvn.tourbookingsytem.dto.request.CustomerRequest;
import com.travelvn.tourbookingsytem.dto.request.booktour.CheckBeforeBookingRequest;
import com.travelvn.tourbookingsytem.dto.response.ApiResponse;
import com.travelvn.tourbookingsytem.dto.response.BookingResponse;
import com.travelvn.tourbookingsytem.enums.BookingStatus;
import com.travelvn.tourbookingsytem.exception.AppException;
import com.travelvn.tourbookingsytem.exception.ErrorCode;
import com.travelvn.tourbookingsytem.mapper.BookingMapper;
import com.travelvn.tourbookingsytem.mapper.CompanionCustomerMapper;
import com.travelvn.tourbookingsytem.mapper.CustomerMapper;
import com.travelvn.tourbookingsytem.model.*;
import com.travelvn.tourbookingsytem.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingService {
    private final UserAccountRepository userAccountRepository;
    private final BookingRepository bookingRepository;
    private final CustomerRepository customerRepository;
    private final CompanionCustomerRepository companionCustomerRepository;
    private final TourUnitRepository tourUnitRepository;

    private final BookingMapper bookingMapper;
    private final CustomerMapper customerMapper;

    private final byte MAX_HOLDING = (byte) 5;

    /**
     * Kiểm tra trước khi chuyển sang trang đặt tour
     *
     * @param checkBeforeBookingRequest Yêu cầu kiểm tra trước khi sang trang đặt tour
     * @return Được chuyển sang trang đặt tour hay không
     */
    public ApiResponse<Integer> checkBeforeBooking(CheckBeforeBookingRequest checkBeforeBookingRequest) {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        //Lấy được account
        UserAccount account = userAccountRepository.findById(name)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        //Lấy đơn vị tour ra
        TourUnit tourUnit = tourUnitRepository.findById(checkBeforeBookingRequest.getTourUnitId())
                .orElseThrow(() -> new AppException(ErrorCode.TOURUNIT_NOT_EXISTED));

        //Kiểm tra số lượng chỗ trống có đủ không
        Long countTotalHolding = tourUnitRepository.countBookingsHolding(Instant.now().toEpochMilli());
        if (countTotalHolding == null) {
            countTotalHolding = 0L;
        }

        int availableCapacity = (int) (tourUnit.getAvailableCapacity() - countTotalHolding);
        if (availableCapacity < 0)
            throw new AppException(ErrorCode.NO_SPACE_BOOKING);

        //Kiểm tra trùng thời gian tour nào đã được đặt hay không
        Customer c = account.getC();
        if (bookingRepository.countTimeConflictTourUnitToBook(c.getId(), tourUnit.getDepartureDate(), tourUnit.getReturnDate(), Instant.now().toEpochMilli()) != 0) {
            throw new AppException(ErrorCode.BOOKING_TIME_CONFLICT);
        }

        //Kiểm tra có hold quá nhiều tour trong ngày không
        if (bookingRepository.countInvalidBookingsByCId(c.getId(), Instant.now().toEpochMilli()) > MAX_HOLDING)
            throw new AppException(ErrorCode.MULTI_HOLDING);

        return ApiResponse.<Integer>builder()
                .message("Có thể đặt tour")
                .result(availableCapacity)
                .build();
    }

    /**
     * Kiểm tra trước đặt tour của service
     *
     * @param account  Tài khoản người dùng
     * @param tourUnit Đơn vị tour đặt
     * @param total    Tổng người đặt
     * @return Được phép đặt hay không
     */
    public boolean checkBeforeBooking(UserAccount account, TourUnit tourUnit, short total) {

        //Kiểm tra số lượng chỗ trống có đủ không
        Long countTotalHolding = tourUnitRepository.countBookingsHolding(Instant.now().toEpochMilli());
        if (countTotalHolding == null) {
            countTotalHolding = 0L;
        }

        //Kiểm tra số lượng chỗ trống có đủ không
        if ((tourUnit.getAvailableCapacity() - countTotalHolding) < total)
            throw new AppException(ErrorCode.NO_SPACE_BOOKING);

        //Kiểm tra trùng thời gian tour nào đã được đặt hay không (P, O, H + expiredAt)
        Customer c = account.getC();
        if (bookingRepository.countTimeConflictTourUnitToBook(c.getId(), tourUnit.getDepartureDate(), tourUnit.getReturnDate(), Instant.now().toEpochMilli()) != 0) {
            throw new AppException(ErrorCode.BOOKING_TIME_CONFLICT);
        }

        //Kiểm tra có hold quá nhiều tour trong ngày không
        if (bookingRepository.countInvalidBookingsByCId(c.getId(), Instant.now().toEpochMilli()) > MAX_HOLDING)
            throw new AppException(ErrorCode.MULTI_HOLDING);

        return true;
    }

    /**
     * DEPRECATED
     * Hàm đặt tour (Không dùng do tích hợp thanh toán payOS và cập nhật DB)
     *
     * @param bookingRequest
     * @return
     */
//    @Transactional
    @Transactional(noRollbackFor = AppException.class)
    public ApiResponse<BookingResponse> bookTour(BookingRequest bookingRequest) {
        log.info("Booking Service NOW");
        try {
            Booking b = bookingRepository.findByPaymentId(bookingRequest.getPayment_id()).orElse(null);

            if (b != null) throw new AppException(ErrorCode.PAYMENT_ID_EXISTED);

            var context = SecurityContextHolder.getContext();
            String name = context.getAuthentication().getName();

            //Lấy được account
            UserAccount account = userAccountRepository.findById(name)
                    .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

            //Lấy đơn vị tour ra
            TourUnit tourUnit = tourUnitRepository.findByIdForUpdate(bookingRequest.getTourUnitId());
            if (tourUnit == null) throw new AppException(ErrorCode.TOURUNIT_NOT_EXISTED);

            log.info("Booking Service1");


            //Tính tổng người và khởi tạo cờ
            short total = (short) (bookingRequest.getAdultNumber() + bookingRequest.getChildNumber() + bookingRequest.getToddlerNumber() + bookingRequest.getBabyNumber());
            checkBeforeBooking(account, tourUnit, total);
            boolean flag = true;

            //Nếu số người vượt quá số lượng còn => Không hợp lệ
            //Không thì cập nhật số lượng còn
            if (total > tourUnit.getAvailableCapacity())
                flag = false;
            else {
                tourUnit.setAvailableCapacity((short) (tourUnit.getAvailableCapacity() - total));
            }

            CustomerRequest customerBookingRequest = bookingRequest.getC();

            //Lấy khách hàng và cập nhật
            Customer c = account.getC();
            c.setFirstname(customerBookingRequest.getFirstname());
            c.setLastname(customerBookingRequest.getLastname());
            c.setAddress(customerBookingRequest.getAddress());
            c.setPhoneNumber(customerBookingRequest.getPhoneNumber());
            c.setGender(customerBookingRequest.getGender());
            c.setDateOfBirth(customerBookingRequest.getDateOfBirth());

            UserAccount temp = c.getUserAccount();
            temp.setEmail(customerBookingRequest.getUserAccount().getEmail());

            c.setUserAccount(temp);
            c = customerRepository.save(c);

            log.info("Booking Service2");

            //Map DTO thành Ent và lưu
            Booking booking = bookingMapper.toBookingTour(bookingRequest);
            booking.setTourUnit(tourUnit);
            booking.setC(c);
            booking.setStatus(flag ? BookingStatus.P.name() : BookingStatus.E.name());
            booking.setBookingDate(Instant.now());
            bookingRepository.save(booking);

            List<CompanionCustomerRequest> companionCustomerRequestSet = bookingRequest.getCompanions();
            companionCustomerRequestSet.forEach(companionCustomerRequest -> {
                CustomerRequest customerRequest = companionCustomerRequest.getC();
                //Tìm khách
                Customer customer = customerRepository.findCustomerByInfo(customerRequest.getFirstname(), customerRequest.getLastname(), customerRequest.getDateOfBirth(), customerRequest.getGender());

                //Không thấy thì tạo mới và lưu
                if (customer == null) {
                    customer = customerMapper.toCustomerToBook(companionCustomerRequest.getC());
                    customerRepository.save(customer);
                }

                //Lưu khách đi cùng
                CompanionCustomer companionCustomer = CompanionCustomer.builder()
                        .booking(booking)
                        .c(customer).build();
                companionCustomerRepository.save(companionCustomer);
            });

            if (!flag) throw new AppException(ErrorCode.BOOKING_TIME_CONFLICT);

            return ApiResponse.<BookingResponse>builder()
                    .message("Thanh toán thành công")
                    .result(BookingResponse.builder()
                            .bookingId(booking.getBookingId())
                            .build())
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Lưu đặt tour trước khi thanh toán (Đã được LOCK bởi Repo)
     *
     * @param bookingRequest Yêu cầu đặt tour
     * @return Được phép đặt tour hay không
     */
    @Transactional
    public ApiResponse<BookingResponse> bookTour2(BookingRequest bookingRequest) {
        log.info("Booking Service NOW");

        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        //Lấy được account
        UserAccount account = userAccountRepository.findById(name)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        //Lấy đơn vị tour ra
        TourUnit tourUnit = tourUnitRepository.findByIdForUpdate(bookingRequest.getTourUnitId());
        if (tourUnit == null) throw new AppException(ErrorCode.TOURUNIT_NOT_EXISTED);

        log.info("Booking Service1");

        //Tính tổng người và kiểm tra đặt tour có hợp lệ
        short total = (short) (bookingRequest.getAdultNumber() + bookingRequest.getChildNumber() + bookingRequest.getToddlerNumber() + bookingRequest.getBabyNumber());
        checkBeforeBooking(account, tourUnit, total);

        //Kiểm tra xem giá có đúng không (Chấp nhận chênh lệch 1 VND)
        BigDecimal expected = calculateTotalAmount(tourUnit, bookingRequest);
        BigDecimal actual = bookingRequest.getTotalAmount();

        BigDecimal difference = expected.subtract(actual).abs();
        log.info("expected: " + expected);
        log.info("actual: " + actual);
        log.info("difference: " + difference);
        if (difference.compareTo(BigDecimal.ONE) > 0) {
            throw new AppException(ErrorCode.INVALID_PAYMENT_AMOUNT);
        }


        //Lấy khách hàng request
        CustomerRequest customerBookingRequest = bookingRequest.getC();

        //Lấy khách hàng và cập nhật
        Customer c = account.getC();
        c.setFirstname(customerBookingRequest.getFirstname());
        c.setLastname(customerBookingRequest.getLastname());
        c.setAddress(customerBookingRequest.getAddress());
        c.setPhoneNumber(customerBookingRequest.getPhoneNumber());
        c.setGender(customerBookingRequest.getGender());
        c.setDateOfBirth(customerBookingRequest.getDateOfBirth());

        UserAccount temp = c.getUserAccount();
        temp.setEmail(customerBookingRequest.getUserAccount().getEmail());

        c.setUserAccount(temp);
        c = customerRepository.save(c);

        log.info("Booking Service2");


        //Map DTO Booking thành Ent và lưu
        Booking booking = bookingMapper.toBookingTour(bookingRequest);

        log.info(booking.toString());

        //Nối vào TourUnit và Customer
        booking.setTourUnit(tourUnit);
        booking.setC(c);

        //Trạng thái tour là Holding
        booking.setStatus(BookingStatus.H.name());
        booking.setBookingDate(Instant.now());
        bookingRepository.save(booking);

        List<CompanionCustomerRequest> companionCustomerRequestSet = bookingRequest.getCompanions();
        companionCustomerRequestSet.forEach(companionCustomerRequest -> {
            CustomerRequest customerRequest = companionCustomerRequest.getC();
            //Tìm khách
            Customer customer = customerRepository.findCustomerByInfo(customerRequest.getFirstname(), customerRequest.getLastname(), customerRequest.getDateOfBirth(), customerRequest.getGender());

            //Không thấy thì tạo mới và lưu
            if (customer == null) {
                customer = customerMapper.toCustomerToBook(companionCustomerRequest.getC());
                customerRepository.save(customer);
            }

            //Lưu khách đi cùng
            CompanionCustomer companionCustomer = CompanionCustomer.builder()
                    .booking(booking)
                    .c(customer).build();
            companionCustomerRepository.save(companionCustomer);
        });

        return ApiResponse.<BookingResponse>builder()
                .message("Đã lưu thông tin giữ chỗ")
                .result(BookingResponse.builder()
                        .bookingId(booking.getBookingId())
                        .build())
                .build();

    }

    public BigDecimal calculateTotalAmount(TourUnit tourUnit, BookingRequest request) {
        // Lấy giá theo loại
        BigDecimal adultPrice = tourUnit.getAdultTourPrice();
        BigDecimal childPrice = tourUnit.getChildTourPrice();
        BigDecimal toddlerPrice = tourUnit.getToddlerTourPrice();
        BigDecimal babyPrice = tourUnit.getBabyTourPrice();
        BigDecimal privateRoomPrice = tourUnit.getPrivateRoomPrice();

        // Áp dụng discount nếu có
        Discount discount = tourUnit.getDiscount(); // discount có thể null
        if (discount != null) {
            if ("%".equals(discount.getDiscountUnit())) {
                adultPrice = adultPrice.multiply(BigDecimal.valueOf(1).subtract(discount.getDiscountValue().divide(BigDecimal.valueOf(100))));
                adultPrice = adultPrice.multiply(BigDecimal.valueOf(1).subtract(discount.getDiscountValue().divide(BigDecimal.valueOf(100))));
                toddlerPrice = toddlerPrice.multiply(BigDecimal.valueOf(1).subtract(discount.getDiscountValue().divide(BigDecimal.valueOf(100))));
            } else if ("VND".equals(discount.getDiscountUnit())) {
                adultPrice = adultPrice.subtract(discount.getDiscountValue());
                childPrice = childPrice.subtract(discount.getDiscountValue());
                toddlerPrice = toddlerPrice.subtract(discount.getDiscountValue());
            }
        }

        //Làm tròn
        adultPrice = adultPrice.setScale(0, RoundingMode.HALF_UP);
        childPrice = childPrice.setScale(0, RoundingMode.HALF_UP);
        toddlerPrice = toddlerPrice.setScale(0, RoundingMode.HALF_UP);
        privateRoomPrice = privateRoomPrice.setScale(0, RoundingMode.HALF_UP);


        // Tính tổng gốc
        BigDecimal total = adultPrice.multiply(BigDecimal.valueOf(request.getAdultNumber()))
                .add(childPrice.multiply(BigDecimal.valueOf(request.getChildNumber())))
                .add(toddlerPrice.multiply(BigDecimal.valueOf(request.getToddlerNumber())))
                .add(babyPrice.multiply(BigDecimal.valueOf(request.getBabyNumber())))
                .add(privateRoomPrice.multiply(BigDecimal.valueOf(request.getPrivateRoomNumber())));

        return total.setScale(0, RoundingMode.HALF_UP); // để giống Math.round()
    }

    /**
     * Xóa toàn bộ đặt tour không hợp lệ
     */
    @Transactional
    public void deleteInvalidBooking() {
        List<Booking> bookings = bookingRepository.findAllByStatusAndExpiredAtLessThan("H", System.currentTimeMillis());
        bookingRepository.deleteAll(bookings); // Hibernate sẽ cascade xóa luôn companionCustomer
    }

    /**
     * Cập nhật trạng thái đặt tour khi thanh toán tour thành công (Từ webhook)
     *
     * @param orderCode Mã đơn hàng do payOS cung cấp
     * @return Kết quả cập nhật
     */
    @Transactional
    public boolean payBooking(long orderCode) {
        Booking booking = bookingRepository.findBookingByOrderCode(orderCode)
                .orElseThrow(() -> new AppException(ErrorCode.BOOKING_ORDER_CODE_NOT_EXISTED));

        booking.setStatus(BookingStatus.P.name());

        short total = (short) (booking.getAdultNumber() + booking.getChildNumber() + booking.getToddlerNumber() + booking.getBabyNumber());

        //Không lấy thông qua Booking do chức năng đặt tour quan trọng nên phải đơn luồng, cập nhật số lượng còn từ từ
        //Chống race condition
        TourUnit tourUnit = tourUnitRepository.findByIdForUpdate(booking.getTourUnit().getTourUnitId());
        tourUnit.setAvailableCapacity((short) (tourUnit.getAvailableCapacity() - total));

        //Lưu booking và tourUnit
        bookingRepository.save(booking);
        tourUnitRepository.save(tourUnit);
        return true;
    }

    /**
     * Trả về mã đặt tour từ orderCode (Authen người đặt)
     *
     * @param orderCode mã đơn hàng của payOS
     * @return mã đặt tour
     */
    public ApiResponse<String> getBooingIdByOrderCode(long orderCode) {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        UserAccount account = userAccountRepository.findById(name)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Booking booking = bookingRepository.findBookingByOrderCode(orderCode)
                .orElseThrow(() -> new AppException(ErrorCode.BOOKING_ORDER_CODE_NOT_EXISTED));

        //Lấy mã đặt tour từ OrderCode mà Booking không phải của mình
        if (!Objects.equals(account.getC().getId(), booking.getC().getId()))
            throw new AppException(ErrorCode.UNAUTHENTICATED);

        return ApiResponse.<String>builder()
                .message("Đặt tour thành công")
                .result(booking.getBookingId())
                .build();
    }
}
