package com.travelvn.tourbookingsytem.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class LastNameValidator implements ConstraintValidator<LastNameConstraint, String> {

    /**
     * \p{L}: Chấp nhận tất cả chữ cái (hỗ trợ Unicode, bao gồm cả tiếng Việt)
     * +: Yêu cầu ít nhất 1 ký tự (dùng @Size để kiểm tra số ký tự).
     */
    private static final String NAME_PATTERN = "^[\\p{L}]+$";

    /**
     * Khởi tạo các thông số cho annotation
     *
     * @param constraintAnnotation
     */
    @Override
    public void initialize(LastNameConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    /**
     * Kiểm tra tên nhập vào chỉ gồm chữ cái
     *
     * @param s Chuỗi truyền vào
     * @param constraintValidatorContext
     * @return Tính hợp lệ của tên
     */
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        //Tạm thời cho phép trống
        if(s == null) return true; //Không để trống tên //Có thể bỏ do bắt validator Size
        return s.matches(NAME_PATTERN); //Kiểm tra tên hợp lệ không
    }

}
