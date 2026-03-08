package ru.rsreu.sea_fishing.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rsreu.sea_fishing.entities.Boat;

public interface BoatRepository extends JpaRepository<Boat, Integer> {
}
