package ru.rsreu.sea_fishing.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FishNameValidator.class)
@Documented
public @interface ValidFishName {
    String message() default "Название рыбы некорректно";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
