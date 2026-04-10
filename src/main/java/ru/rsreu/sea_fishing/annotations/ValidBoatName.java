package ru.rsreu.sea_fishing.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BoatNameValidator.class)
@Documented
public @interface ValidBoatName {
    String message() default "Название лодки некорректно";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

