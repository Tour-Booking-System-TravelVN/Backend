package com.travelvn.tourbookingsytem.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized exception", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "Invalid message key", HttpStatus.BAD_REQUEST),
    USERACCOUNT_EXISTED(1002, "User existed", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1003, "Tên người dùng phải có độ dài từ {min} đến 40 ký tự.", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1004, "Mật khẩu phải có độ dài từ {min} đến 20 ký tự.", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1005,"User not existed", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1006,"Unauthenticated", HttpStatus.UNAUTHORIZED), //401
    UNAUTHORIZED(1007,"You do not have permission", HttpStatus.FORBIDDEN), //403
    INVALID_DOB(1008, "Your age must be least at {min}", HttpStatus.BAD_REQUEST),

    INVALID_FIRSTNAME(1009, "Họ và họ đệm chỉ chứa ký tự chữ và dấu cách.", HttpStatus.BAD_REQUEST),
    INVALID_LASTNAME(1010, "Tên chỉ chứa ký tự chữ.", HttpStatus.BAD_REQUEST),
    INVALID_EMAIL(1011, "Email không hợp lệ.", HttpStatus.BAD_REQUEST),
    INVALID_PHONENUMBER(1012, "Số điện thoại không hợp lệ.", HttpStatus.BAD_REQUEST),
    INVALID_CI(1013, "Số căn cước công dân không hợp lệ.", HttpStatus.BAD_REQUEST),
    INVALID_DEPARTURE_DATE(1014, "Ngày xuất phát không hợp lệ.", HttpStatus.BAD_REQUEST),

    INVALID_BOOKING_ID(1015, "Mã đặt tour gồm 20 ký tự", HttpStatus.BAD_REQUEST),
    NO_SPACE_BOOKING(1016, "Tour không còn chỗ trống", HttpStatus.BAD_REQUEST),
    MULTI_HOLDING(1017, "Bạn đã đặt nhiều tour hôm nay nhưng không thanh toán. Hãy quay lại vào ngày mai nhé.", HttpStatus.BAD_REQUEST),
    BOOKING_ORDER_CODE_NOT_EXISTED(1018, "Không thấy mã đặt tour", HttpStatus.NOT_FOUND),

    ACCOUNT_LOCKED(1019, "Tài khoản đã bị khóa.", HttpStatus.FORBIDDEN),
    NO_FESTIVALS_CAROUSEL(1020, "Không có lễ hội để hiển thị", HttpStatus.NOT_FOUND),

    UNMATCHED_PWD(1021, "Mật khẩu không khớp", HttpStatus.BAD_REQUEST),
    DUPLICATE_PWD(1022, "Mật khẩu mới trùng mật khẩu cũ", HttpStatus.BAD_REQUEST),

    BOOKING_NOT_EXISTED(1023, "Không tìm thấy mã đặt tour này.", HttpStatus.NOT_FOUND),
    BOOKING_NOT_CANCELED(1024, "Không thể hủy tour.", HttpStatus.BAD_REQUEST),

    BOOKING_NOT_DONE(1025, "Không thể đánh giá tour chưa kết thúc.", HttpStatus.BAD_REQUEST),
    TOUR_RATING_EXISTED(1026, "Chỉ được đánh giá tour 1 lần", HttpStatus.BAD_REQUEST),

    TOURUNIT_NOT_EXISTED(2000,"Không tìm thấy đơn vị tour.", HttpStatus.NOT_FOUND),
    BOOKING_TIME_CONFLICT(2001, "Thời gian tour diễn ra xung đột với tour của bạn.", HttpStatus.BAD_REQUEST),
    BOOKING_SYSTEM_CONFLICT(2002, "Xin chào,\n" +
            "\n" +
            "Rất tiếc, tour mà bạn chọn hiện đã hết chỗ tại thời điểm thanh toán.\n" +
            "Số tiền bạn đã thanh toán sẽ được hoàn lại trong thời gian sớm nhất.\n" +
            "\n" +
            "Chúng tôi thành thật xin lỗi về sự bất tiện này.", HttpStatus.BAD_REQUEST),
    PAYMENT_ID_EXISTED(2003,"Mã thanh toán không hợp lệ.", HttpStatus.BAD_REQUEST),
    INVALID_PAYMENT_AMOUNT(2004,"Tổng tiền thanh toán không hợp lệ", HttpStatus.BAD_REQUEST),
    ;

    private int code;
    private String message;
    private HttpStatusCode  statusCode;
}
