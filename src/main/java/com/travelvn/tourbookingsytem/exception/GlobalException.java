package com.travelvn.tourbookingsytem.exception;

import com.travelvn.tourbookingsytem.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Objects;

@ControllerAdvice
public class GlobalException {

    /**
     * Xử lý lỗi bất kỳ
     *
     * @param e Lỗi
     * @return API chứa thông tin lỗi
     */
    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiResponse> handlingRunTimeException(RuntimeException e) {
        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        apiResponse.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());

        return ResponseEntity.badRequest().body(apiResponse);
    }

    /**
     * Xử lý lỗi validation với dto request
     *
     * @param e Lỗi
     * @return API chứa thông tin lỗi
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse> handlingMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String enumKey = e.getFieldError().getDefaultMessage();
        ErrorCode errorCode = ErrorCode.INVALID_KEY;

        try {
            errorCode = ErrorCode.valueOf(enumKey);
        } catch (IllegalArgumentException ex) {

        }

        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());

        return ResponseEntity.badRequest().body(apiResponse);
    }

    /**
     * Xử lý lỗi ở service
     *
     * @param e Lỗi
     * @return API chứa thông tin lỗi
     */
    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse> handlingAppException(AppException e) {
        ErrorCode errorCode = e.getErrorCode();
        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());

        return ResponseEntity.badRequest().body(apiResponse);
    }
}
