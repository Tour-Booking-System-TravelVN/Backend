package com.travelvn.tourbookingsytem.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class DobValidator implements ConstraintValidator<DobConstraint, LocalDateTime> {

    private int min;

    /**
     * Khởi tạo các thông số cho annotation
     *
     * @param constraintAnnotation
     */
    @Override
    public void initialize(DobConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);

        min = constraintAnnotation.min();
    }

    /**
     * Kiểm tra dob có hợp lệ
     *
     * @param value Giá trị được truyền vào
     * @param constraintValidatorContext
     * @return
     */
    @Override
    public boolean isValid(LocalDateTime value, ConstraintValidatorContext constraintValidatorContext) {

        if(Objects.isNull(value))
            return true;

        long years = ChronoUnit.YEARS.between(value, LocalDateTime.now());

        return years >= min;
    }
}
