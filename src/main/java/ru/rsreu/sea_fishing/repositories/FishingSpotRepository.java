package ru.rsreu.sea_fishing.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.rsreu.sea_fishing.entities.FishingSpot;

import java.util.List;

public interface FishingSpotRepository extends JpaRepository<FishingSpot, Integer> {

    @Query("""
            select distinct spot
            from FishingSpot spot
            left join fetch spot.fauna fauna
            left join fetch fauna.fish
            order by spot.spotName asc
            """)
    List<FishingSpot> findAllWithFauna();
}
