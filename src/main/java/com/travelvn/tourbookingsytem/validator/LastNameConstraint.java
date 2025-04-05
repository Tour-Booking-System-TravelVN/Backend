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
        validatedBy = {LastNameValidator.class}
)
public @interface LastNameConstraint {
    String message() default "Tên không hợp lệ. Chỉ chứa chữ cái.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
