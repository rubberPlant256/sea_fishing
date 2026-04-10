package ru.rsreu.sea_fishing.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rsreu.sea_fishing.entities.FishType;

import java.util.List;

public interface FishTypeRepository extends JpaRepository<FishType, Integer> {

    List<FishType> findAllByOrderByFishNameAsc();
}
