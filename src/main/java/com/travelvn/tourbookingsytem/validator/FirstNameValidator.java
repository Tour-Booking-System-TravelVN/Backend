package com.travelvn.tourbookingsytem.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FirstNameValidator implements ConstraintValidator<FirstNameConstraint, String> {

    /**
     * \p{L}\s: Chấp nhận tất cả chữ cái (hỗ trợ Unicode, bao gồm cả tiếng Việt) và dấu cách
     * .: Yêu cầu ít nhất 0 ký tự (dùng @Size để kiểm tra số ký tự).
     */
    private static final String NAME_PATTERN = "^[\\p{L}\\s].$";

    /**
     * Khởi tạo các thông số cho annotation
     *
     * @param constraintAnnotation
     */
    @Override
    public void initialize(FirstNameConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    /**
     * Kiểm tra họ và tên đệm nhập vào chỉ gồm chữ cái và dấu cách
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
