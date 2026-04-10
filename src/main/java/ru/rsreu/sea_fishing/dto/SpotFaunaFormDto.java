package ru.rsreu.sea_fishing.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpotFaunaFormDto {

    @NotBlank(message = "Рыба обязательна")
    @Min(value = 1, message = "Некорректный fishId")
    private String fishId;

    @NotBlank(message = "Сезонность обязательна")
    @Size(max = 255, message = "Сезонность не должна быть больше 255 символов")
    @Pattern(
            regexp = "^[\\p{L} ]+$",
            message = "Сезонность должна содержать только буквы"
    )
    private String seasonality;
}
