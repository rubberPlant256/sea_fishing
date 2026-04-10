package ru.rsreu.sea_fishing.mongo.dto;

import java.util.Set;

public record FishingSpotView(
        String spotId,
        String spotName,
        Integer depthMeters,
        String bottomType,
        Set<SpotFaunaView> fauna
) {}
