package ru.rsreu.sea_fishing.mongo.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record BoatView(
        String boatId,
        String boatName,
        Integer capacity,
        BigDecimal pricePerHour
) {}
