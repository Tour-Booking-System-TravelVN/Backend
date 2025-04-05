package com.travelvn.tourbookingsytem.validator;

import com.travelvn.tourbookingsytem.util.Decryption;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.Field;
import java.time.LocalDate;

public class CitizenIdValidator implements ConstraintValidator<CitizenIdConstraint, Object> {

    /**
     * Dùng tiện ích mã hóa
     */
    private static final Decryption decryption = new Decryption();

    private String citizenIdFieldName;
    private String dateOfBirthFieldName;
    private String genderFieldName;

    /**
     * Khởi tạo các thông số cho annotation
     *
     * @param constraintAnnotation
     */
    @Override
    public void initialize(CitizenIdConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.citizenIdFieldName = constraintAnnotation.citizenId();
        this.dateOfBirthFieldName = constraintAnnotation.dateOfBirth();
        this.genderFieldName = constraintAnnotation.gender();
    }

    /**
     * Kiểm tra tính hợp lệ của cccd theo 6 số đầu
     *
     * @param value các trường cho vào kiểm tra cùng
     * @param constraintValidatorContext
     * @return
     */
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        try {
            //Dùng reflection lấy các thuộc tính
            //Lấy số cccd
            Field citizenIdField = value.getClass().getDeclaredField(citizenIdFieldName);
            citizenIdField.setAccessible(true);
            String citizenId = (String) citizenIdField.get(value);

            //Lấy ngày sinh
            Field dateOfBirthField = value.getClass().getDeclaredField(dateOfBirthFieldName);
            dateOfBirthField.setAccessible(true);
            LocalDate dob = (LocalDate) dateOfBirthField.get(value);

            //Lấy giới tính
            Field genderField = value.getClass().getDeclaredField(genderFieldName);
            genderField.setAccessible(true);
            Boolean gender = (Boolean) genderField.get(value);

            int year = dob.getYear();

            return decryption.isFirstThreeDigitsValid(citizenId) && decryption.checkBirthOfDateAndGender(citizenId, year, gender);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return false;
        }
    }

}
