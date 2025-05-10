package com.travelvn.tourbookingsytem.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class DobValidator implements ConstraintValidator<DobConstraint, LocalDateTime> {

    private int min;

    @Override
    public void initialize(DobConstraint constraintAnnotation) {
        this.min = constraintAnnotation.min();
    }

    @Override
    public boolean isValid(LocalDateTime value, ConstraintValidatorContext context) {
        if (Objects.isNull(value)) {
            return true;
        }

        if (value.isAfter(LocalDateTime.now())) {
            return false; // Không được lớn hơn hiện tại
        }

        long years = ChronoUnit.YEARS.between(value.toLocalDate(), LocalDateTime.now().toLocalDate());
        return years >= min;
    }
}
