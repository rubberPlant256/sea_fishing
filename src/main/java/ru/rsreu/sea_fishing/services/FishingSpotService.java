package ru.rsreu.sea_fishing.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.rsreu.sea_fishing.dto.FishingSpotFormDto;
import ru.rsreu.sea_fishing.dto.SpotFaunaFormDto;
import ru.rsreu.sea_fishing.entities.FishType;
import ru.rsreu.sea_fishing.entities.FishingSpot;
import ru.rsreu.sea_fishing.entities.SpotFauna;
import ru.rsreu.sea_fishing.repositories.FishTypeRepository;
import ru.rsreu.sea_fishing.repositories.FishingSpotRepository;

import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Profile("sql")
@Service
@RequiredArgsConstructor
public class FishingSpotService {

    private final FishingSpotRepository fishingSpotRepository;
    private final FishTypeRepository fishTypeRepository;

    public List<FishingSpot> findAll() {
        return fishingSpotRepository.findAllWithFauna();
    }

    @Transactional
    public void save(FishingSpotFormDto form) {
        FishingSpot spot = new FishingSpot();
        spot.setSpotName(form.getSpotName());
        spot.setDepthMeters(form.getDepthMeters());
        spot.setBottomType(form.getBottomType());
        Set<Integer> processedFishIds = new HashSet<>();

        if (form.getFauna() != null) {
            for (SpotFaunaFormDto faunaForm : form.getFauna()) {
                if (faunaForm.getFishId() == null || !processedFishIds.add(Integer.valueOf(faunaForm.getFishId()))) {
                    continue;
                }

                FishType fish = fishTypeRepository.findById(Integer.valueOf(faunaForm.getFishId()))
                        .orElseThrow(() -> new RuntimeException("Рыба не найдена"));

                SpotFauna spotFauna = new SpotFauna();
                spotFauna.setSpot(spot);
                spotFauna.setFish(fish);
                spotFauna.setSeasonality(faunaForm.getSeasonality());
                spot.getFauna().add(spotFauna);
            }
        }

        sortFauna(spot);
        fishingSpotRepository.save(spot);
    }

    @Transactional
    public void addFauna(Integer spotId, Integer fishId, String seasonality) {
        FishingSpot spot = fishingSpotRepository.findById(spotId)
                .orElseThrow(() -> new RuntimeException("Место ловли не найдено"));

        FishType fish = fishTypeRepository.findById(fishId)
                .orElseThrow(() -> new RuntimeException("Рыба не найдена"));

        spot.getFauna().stream()
                .filter(fauna -> Objects.equals(fauna.getFish().getFishId(), fishId))
                .findFirst()
                .ifPresentOrElse(
                        existing -> existing.setSeasonality(seasonality),
                        () -> {
                            SpotFauna spotFauna = new SpotFauna();
                            spotFauna.setSpot(spot);
                            spotFauna.setFish(fish);
                            spotFauna.setSeasonality(seasonality);
                            spot.getFauna().add(spotFauna);
                        }
                );

        sortFauna(spot);
    }

    @Transactional
    public void deleteFauna(Integer spotId, Integer fishId) {
        FishingSpot spot = fishingSpotRepository.findById(spotId)
                .orElseThrow(() -> new RuntimeException("Место ловли не найдено"));

        spot.getFauna().removeIf(fauna -> Objects.equals(fauna.getFish().getFishId(), fishId));
        fishingSpotRepository.save(spot);
    }

    public void deleteById(Integer id) {
        fishingSpotRepository.deleteById(id);
    }

    private void sortFauna(FishingSpot spot) {
        Set<SpotFauna> sorted = spot.getFauna().stream()
                .sorted(Comparator.comparing(
                        fauna -> fauna.getFish().getFishName(),
                        String.CASE_INSENSITIVE_ORDER
                ))
                .collect(java.util.stream.Collectors.toCollection(LinkedHashSet::new));

        spot.getFauna().clear();
        spot.getFauna().addAll(sorted);
    }

}
