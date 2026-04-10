package ru.rsreu.sea_fishing.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FishingSpotFormDto {

    @NotBlank(message = "Название места ловли обязательно")
    @Size(max = 100, message = "Название места не должно быть больше 100 символов")
    private String spotName;

    @NotNull(message = "Глубина обязательна")
    @Min(value = 0, message = "Глубина должна быть >= 0")
    private Integer depthMeters;

    @NotBlank(message = "Тип дна обязателен")
    @Size(max = 50, message = "Тип дна не должен быть больше 50 символов")
    @Pattern(
            regexp = "^[\\p{L} ]+$",
            message = "Тип дна должен содержать только буквы"
    )
    private String bottomType;

    @NotNull(message = "Фауна не должна быть пустой")
    @Size(min = 1, message = "Добавьте хотя бы одну строку фауны")
    @Valid
    private List<SpotFaunaFormDto> fauna = new ArrayList<>();
}
