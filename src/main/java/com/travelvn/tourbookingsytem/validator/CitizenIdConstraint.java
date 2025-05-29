package com.travelvn.tourbookingsytem.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.LocalDate;

@Target({ElementType.TYPE}) //Đối tượng sử dụng là obj
@Retention(RetentionPolicy.RUNTIME)
@Constraint(
        validatedBy = {CitizenIdValidator.class}
)
public @interface CitizenIdConstraint {
    String message() default "Số điện thoại không hợp lệ.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String citizenId();

    String dateOfBirth();

    String gender();

}
