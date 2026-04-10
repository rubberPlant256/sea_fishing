package ru.rsreu.sea_fishing.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FishNameValidator implements ConstraintValidator<ValidFishName, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return false;
        String v = value.trim();

        // Проверка 1: минимальная длина
        if (v.length() < 2 || v.length() > 50) return false;

        // Проверка 2: допустимые символы (буквы/пробел/дефис/точка)
        return v.matches("[\\p{L}][\\p{L} \\-\\.]{1,49}");
    }
}
