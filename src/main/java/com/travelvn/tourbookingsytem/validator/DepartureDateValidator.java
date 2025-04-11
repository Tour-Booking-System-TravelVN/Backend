package com.travelvn.tourbookingsytem.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class DepartureDateValidator implements ConstraintValidator<DepartureDateConstraint, LocalDate> {

    private int min;

    /**
     * Khởi tạo các thông số cho annotation
     *
     * @param constraintAnnotation
     */
    @Override
    public void initialize(DepartureDateConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    /**
     * Kiểm tra ngày đi có hợp lệ
     *
     * @param value Giá trị được truyền vào
     * @param constraintValidatorContext
     * @return
     */
    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext constraintValidatorContext) {

        if(Objects.isNull(value))
            return true;

        return !value.isBefore(ChronoLocalDate.from(Instant.now()));
    }
}
