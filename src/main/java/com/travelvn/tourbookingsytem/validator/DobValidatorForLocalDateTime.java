package com.travelvn.tourbookingsytem.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class DobValidatorForLocalDateTime implements ConstraintValidator<DobConstraint, LocalDateTime> {
    private int min;

    @Override
    public void initialize(DobConstraint constraintAnnotation) {
        min = constraintAnnotation.min();
    }

    @Override
    public boolean isValid(LocalDateTime value, ConstraintValidatorContext context) {
        if (value == null) return true;
        long years = ChronoUnit.YEARS.between(value, LocalDateTime.now());
        return years >= min;
    }
}

