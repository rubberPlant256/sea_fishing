package ru.rsreu.sea_fishing.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import ru.rsreu.sea_fishing.annotations.ValidBoatName;

import java.math.BigDecimal;

public record BoatFormDto(

        String boatId,

        @ValidBoatName
        String boatName,

        @NotNull(message = "Вместимость обязательна")
        @Min(value = 1, message = "Вместимость должна быть >= 1")
        Integer capacity,

        @NotNull(message = "Цена обязательна")
        @DecimalMin(value = "0.0", inclusive = true, message = "Цена должна быть >= 0")
        BigDecimal pricePerHour
) {}
