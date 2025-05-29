package com.travelvn.tourbookingsytem.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumberConstraint, String> {

    /**
     * Số điện thoại 10 ký từ và 3 số đầu được sử dụng ở VN.
     */
    private static final String NAME_PATTERN = "^(032|033|034|035|036|037|038|039|096|097|098|086|083|084|085|081|082|088|091|094|070|079|077|076|078|090|093|089|056|058|092|059|099)[0-9]{7}$";

    /**
     * Khởi tạo các thông số cho annotation
     *
     * @param constraintAnnotation
     */
    @Override
    public void initialize(PhoneNumberConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    /**
     * Kiểm tra số điện thoại có hợp lệ
     *
     * @param s Chuỗi truyền vào
     * @param constraintValidatorContext
     * @return Tính hợp lệ của sđt
     */
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        //Tạm thời cho phép trống
        if(s == null) return true; //Không để trống tên //Có thể bỏ do bắt validator Size
        return s.matches(NAME_PATTERN); //Kiểm tra tên hợp lệ không
    }

}
