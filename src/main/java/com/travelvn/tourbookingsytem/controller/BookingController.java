package com.travelvn.tourbookingsytem.controller;

import com.travelvn.tourbookingsytem.dto.request.BookingRequest;
import com.travelvn.tourbookingsytem.dto.request.booktour.CheckBeforeBookingRequest;
import com.travelvn.tourbookingsytem.dto.response.ApiResponse;
import com.travelvn.tourbookingsytem.dto.response.BookingResponse;
import com.travelvn.tourbookingsytem.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/booking")
public class BookingController {
    private final BookingService bookingService;

    /**
     * Kiểm tra trước khi đến form đặt tour
     * @param checkBeforeBookingRequest
     * @return
     */
    @GetMapping("/checkbeforebooking")
    public ApiResponse<Integer> checkBeforeBooking(@ModelAttribute CheckBeforeBookingRequest checkBeforeBookingRequest) {
        return bookingService.checkBeforeBooking(checkBeforeBookingRequest);
    }

    /** DEPRECATED
     * Hàm đặt tour (Không tích hợp thanh toán)
     * @param bookingRequest Yêu cầu đặt tour
     * @return Mã đặt tour
     */
    @PostMapping("/booktour")
    public ApiResponse<BookingResponse> bookTour(@Valid @RequestBody BookingRequest bookingRequest) {
        log.info("Booking request: {}", bookingRequest);
        return bookingService.bookTour(bookingRequest);
    }

    /**
     * Lấy mã đặt tour
     * @param orderCode Mã đơn hàng
     * @return Mã đặt tour
     */
    @GetMapping("/{orderCode}")
    public ApiResponse<String> getBooking(@PathVariable long orderCode) {
        return bookingService.getBooingIdByOrderCode(orderCode);
    }

}
