package ru.rsreu.sea_fishing.mongo.dto;

public record SpotFaunaView(
        FishView fish,
        String seasonality
) {}
