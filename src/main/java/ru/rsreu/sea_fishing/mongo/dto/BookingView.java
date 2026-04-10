package ru.rsreu.sea_fishing.mongo.dto;

public record BookingView(
        String bookingId,
        UserView userView, ExcursionView excursion,
        FishingSpotView spot
) {}
