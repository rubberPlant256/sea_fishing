package ru.rsreu.sea_fishing.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.rsreu.sea_fishing.entities.FishingSpot;

public interface FishingSpotRepository extends CrudRepository<FishingSpot, Integer> {

}
