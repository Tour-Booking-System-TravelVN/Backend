package com.travelvn.tourbookingsytem.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class DobValidatorForLocalDate implements ConstraintValidator<DobConstraint, LocalDate> {

    private int min;

    @Override
    public void initialize(DobConstraint constraintAnnotation) {
        this.min = constraintAnnotation.min();
    }

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        if (Objects.isNull(value)) return true; // Cho phép null (NotNull xử lý riêng)
        if (value.isAfter(LocalDate.now())) return false; // Không thể lớn hơn hôm nay

        long years = ChronoUnit.YEARS.between(value, LocalDate.now());
        return years >= min;
    }
}
