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
    ;

    private int code;
    private String message;
    private HttpStatusCode  statusCode;
}
