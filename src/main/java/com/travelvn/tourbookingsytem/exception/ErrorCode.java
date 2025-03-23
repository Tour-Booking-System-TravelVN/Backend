package com.travelvn.tourbookingsytem.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized exception"),
    INVALID_KEY(1001, "Invalid message key"),
    USERACCOUNT_EXISTED(1002, "User existed"),
    USERNAME_INVALID(1003, "Tên người dùng phải có độ dài từ 10 đến 40 ký tự."),
    PASSWORD_INVALID(1004, "Mật khẩu phải có độ dài từ 10 đến 20 ký tự."),
    USER_NOT_EXISTED(1005,"User not existed"),
    UNAUTHENTICATED(1006,"Unauthenticated"),
    ;

    private int code;
    private String message;
}
