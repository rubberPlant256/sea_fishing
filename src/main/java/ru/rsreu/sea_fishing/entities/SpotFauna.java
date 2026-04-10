package ru.rsreu.sea_fishing.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ru.rsreu.sea_fishing.entities.compositeId.SpotFaunaId;

import java.util.Objects;

@Data
@Entity
@Table(name = "spot_fauna")
@IdClass(SpotFaunaId.class)
public class SpotFauna {

    @Id
    @ManyToOne
    @JoinColumn(name = "spot_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private FishingSpot spot;

    @Id
    @ManyToOne
    @JoinColumn(name = "fish_id")
    private FishType fish;

    private String seasonality;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SpotFauna that)) return false;
        return Objects.equals(spot != null ? spot.getSpotId() : null,
                that.spot != null ? that.spot.getSpotId() : null)
                && Objects.equals(fish != null ? fish.getFishId() : null,
                that.fish != null ? that.fish.getFishId() : null);
    }

    @Override
    public int hashCode() {
        return Objects.hash(spot != null ? spot.getSpotId() : null,
                fish != null ? fish.getFishId() : null);
    }
}