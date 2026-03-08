package ru.rsreu.sea_fishing.entities.compositeId;

import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpotFaunaId implements Serializable {
    private Integer spot;
    private Integer fish;
}