package com.travelvn.tourbookingsytem.exception;

import com.travelvn.tourbookingsytem.dto.response.ApiAdResponse;
import jakarta.validation.ConstraintViolation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;
import java.util.Objects;

@Slf4j
@ControllerAdvice
public class GlobalException {

    private static final String MIN_ATTRIBUTE = "min";

    /**
     * Xử lý lỗi bất kỳ
     *
     * @param e Lỗi
     * @return API chứa thông tin lỗi
     */
    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiAdResponse> handlingRunTimeException(RuntimeException e) {
        ApiAdResponse apiAdResponse = new ApiAdResponse();

        apiAdResponse.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        apiAdResponse.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());

        return ResponseEntity
                .status(ErrorCode.UNCATEGORIZED_EXCEPTION.getStatusCode())
                .body(apiAdResponse);
    }

    /**
     * Xử lý lỗi validation với dto request
     *
     * @param e Lỗi
     * @return API chứa thông tin lỗi
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiAdResponse> handlingMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String enumKey = e.getFieldError().getDefaultMessage();
        ErrorCode errorCode = ErrorCode.INVALID_KEY;

        Map<String, Object> attributes = null;

        try {
            errorCode = ErrorCode.valueOf(enumKey);

            //BindingResult là một giao diện chứa kết quả của quá trình liên kết dữ liệu từ form hoặc request body đến controller. Nó bao gồm danh sách các lỗi, nếu có.
            //unwrap(ConstraintViolation.class) cố gắng chuyển đổi (unwrap) đối tượng ObjectError đầu tiên thành một đối tượng ConstraintViolation.
            //Trích xuất lỗi vi phạm ràng buộc dữ liệu
            var constraintViolation = e.getBindingResult()
                    .getAllErrors().getFirst().unwrap(ConstraintViolation.class);

            attributes = constraintViolation.getConstraintDescriptor().getAttributes();

            log.info(attributes.toString());

        } catch (IllegalArgumentException ex) {

        }

        ApiAdResponse apiAdResponse = new ApiAdResponse();

        apiAdResponse.setCode(errorCode.getCode());
        apiAdResponse.setMessage(Objects.nonNull(attributes) ?
                mapAttribute(errorCode.getMessage(), attributes)
                : errorCode.getMessage());

        return ResponseEntity
                .status(errorCode.getStatusCode())
                .body(apiAdResponse);
    }

    /**
     * Thay thế placeholder của ErrorCode trong message bằng attribute từ map
     *
     * @param message Một chuỗi (String) chứa placeholder muốn thay thế.
     * @param attributes Một Map (ánh xạ) chứa các thuộc tính, trong đó key là tên thuộc tính và value là giá trị thuộc tính.
     * @return Chuỗi kết quả sau khi thay thế được trả về.
     */
    private String mapAttribute(String message, Map<String, Object> attributes) {
        String minValue = String.valueOf(attributes.get(MIN_ATTRIBUTE).toString());

        return message.replace("{"+MIN_ATTRIBUTE+"}", minValue);
    }

    /**
     * Xử lý lỗi ở service
     *
     * @param e Lỗi
     * @return API chứa thông tin lỗi
     */
    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiAdResponse> handlingAppException(AppException e) {
        ErrorCode errorCode = e.getErrorCode();
        ApiAdResponse apiAdResponse = new ApiAdResponse();

        apiAdResponse.setCode(errorCode.getCode());
        apiAdResponse.setMessage(errorCode.getMessage());

        return ResponseEntity
                .status(errorCode.getStatusCode())
                .body(apiAdResponse);
    }

    /**
     * Xử lý lỗi không có quyền truy cập (403)
     *
     * @param e Lỗi
     * @return API chứa thông tin lỗi
     */
    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ApiAdResponse> handlingAccessDeniedException(AccessDeniedException e) {
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;

        return ResponseEntity.status(errorCode.getStatusCode()).body(
                ApiAdResponse.builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiAdResponse<Object>> handlingDataIntegrityViolationException(DataIntegrityViolationException ex) {
        ApiAdResponse<Object> apiAdResponse = ApiAdResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message("Data integrity violation: " + ex.getRootCause().getMessage())
                .build();
        return new ResponseEntity<>(apiAdResponse, HttpStatus.BAD_REQUEST);
    }
}
