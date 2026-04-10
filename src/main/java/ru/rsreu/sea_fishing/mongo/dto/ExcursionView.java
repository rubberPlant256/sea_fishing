package ru.rsreu.sea_fishing.mongo.dto;

import java.time.LocalDateTime;

public record ExcursionView(
        String id,
        BoatView boat,
        LocalDateTime startTime,
        LocalDateTime endTime
) {}
