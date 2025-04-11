package com.travelvn.tourbookingsytem.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(
        validatedBy = {DepartureDateValidator.class}
)
public @interface DepartureDateConstraint {
    String message() default "Invalid departure date";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
