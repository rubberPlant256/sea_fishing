package ru.rsreu.sea_fishing.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class BoatNameValidator implements ConstraintValidator<ValidBoatName, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return false;
        String v = value.trim();

        // 1) длина
        if (v.length() < 3 || v.length() > 100) return false;

        // 2) допустимые символы
        return v.matches("[\\p{L}0-9][\\p{L}0-9\\s\\-\\._]{1,99}");
    }
}

