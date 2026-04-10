package ru.rsreu.sea_fishing.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import ru.rsreu.sea_fishing.annotations.ValidFishName;

public record FishFormDto(

        String fishId,

        @ValidFishName
        @NotBlank(message = "Название рыбы обязательно")
        @Size(max = 50, message = "Название рыбы не должно быть больше 50 символов")
        String fishName,

        @NotBlank(message = "Уровень сложности обязателен")
        @Size(max = 20, message = "Уровень сложности не должен быть больше 20 символов")
        @Pattern(
                regexp = "^(низкий|средний|высокий)$",
                flags = Pattern.Flag.CASE_INSENSITIVE,
                message = "Укажите уровень: низкий, средний или высокий"
        )
        String difficultyLevel
) {}
