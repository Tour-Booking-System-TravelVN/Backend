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
        validatedBy = {FirstNameValidator.class}
)
public @interface FirstNameConstraint {
    String message() default "Họ và họ đệm không hợp lệ. Chỉ chứa chữ cái và dấu cách.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
