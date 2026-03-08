package ru.rsreu.sea_fishing.entities;

import jakarta.persistence.*;
import lombok.Data;
import ru.rsreu.sea_fishing.entities.compositeId.SpotFaunaId;

@Data
@Entity
@Table(name = "spot_fauna")
@IdClass(SpotFaunaId.class)
public class SpotFauna {

    @Id
    @ManyToOne
    @JoinColumn(name = "spot_id")
    private FishingSpot spot;

    @Id
    @ManyToOne
    @JoinColumn(name = "fish_id")
    private FishType fish;

    private String seasonality;
}