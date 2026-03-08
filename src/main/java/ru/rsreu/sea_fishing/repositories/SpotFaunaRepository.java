package ru.rsreu.sea_fishing.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rsreu.sea_fishing.entities.SpotFauna;
import ru.rsreu.sea_fishing.entities.compositeId.SpotFaunaId;

public interface SpotFaunaRepository extends JpaRepository<SpotFauna, SpotFaunaId> {

}
