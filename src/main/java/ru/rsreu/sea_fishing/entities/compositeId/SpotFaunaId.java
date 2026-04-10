package ru.rsreu.sea_fishing.entities.compositeId;

import java.io.Serializable;
import java.util.Objects;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpotFaunaId implements Serializable {
    private Integer spot;
    private Integer fish;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SpotFaunaId)) return false;
        SpotFaunaId that = (SpotFaunaId) o;
        return Objects.equals(spot, that.spot) && Objects.equals(fish, that.fish);
    }

    @Override
    public int hashCode() {
        return Objects.hash(spot, fish);
    }
}