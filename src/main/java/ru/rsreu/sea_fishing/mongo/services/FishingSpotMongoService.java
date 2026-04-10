package ru.rsreu.sea_fishing.mongo.services;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.rsreu.sea_fishing.dto.FishingSpotFormDto;
import ru.rsreu.sea_fishing.dto.SpotFaunaFormDto;
import ru.rsreu.sea_fishing.mongo.document.FishingSpotDocument;
import ru.rsreu.sea_fishing.mongo.document.FishTypeDocument;
import ru.rsreu.sea_fishing.mongo.document.SpotFaunaEmbedded;
import ru.rsreu.sea_fishing.mongo.repository.FishTypeMongoRepository;
import ru.rsreu.sea_fishing.mongo.repository.FishingSpotMongoRepository;

import java.util.*;
import java.util.stream.Collectors;

@Profile("mongo")
@Service
@RequiredArgsConstructor
public class FishingSpotMongoService {

    private final FishingSpotMongoRepository fishingSpotMongoRepository;
    private final FishTypeMongoRepository fishTypeMongoRepository;

    public List<FishingSpotDocument> findAll() {
        return fishingSpotMongoRepository.findAll();
    }

    public void save(FishingSpotFormDto form) {
        FishingSpotDocument doc = new FishingSpotDocument();
        doc.setSpotName(form.getSpotName());
        doc.setDepthMeters(form.getDepthMeters());
        doc.setBottomType(form.getBottomType());

        List<SpotFaunaEmbedded> fauna = new ArrayList<>();
        if (form.getFauna() != null) {
            Set<ObjectId> processed = new HashSet<>();
            for (SpotFaunaFormDto f : form.getFauna()) {
                if (f.getFishId() == null || f.getFishId().isBlank()) continue;

                ObjectId fishObjId = new ObjectId(f.getFishId());
                if (!processed.add(fishObjId)) continue;

                SpotFaunaEmbedded emb = new SpotFaunaEmbedded();
                emb.setFishId(fishObjId);
                emb.setSeasonality(f.getSeasonality());
                fauna.add(emb);
            }
        }

        doc.setFauna(sortFaunaByFishName(fauna));
        fishingSpotMongoRepository.save(doc);
    }

    public void addFauna(String spotId, String fishId, String seasonality) {
        FishingSpotDocument spot = fishingSpotMongoRepository.findById(new ObjectId(spotId))
                .orElseThrow(() -> new RuntimeException("Место ловли не найдено"));

        ObjectId fishObjId = new ObjectId(fishId);

        Optional<SpotFaunaEmbedded> existing = spot.getFauna().stream()
                .filter(x -> Objects.equals(x.getFishId(), fishObjId))
                .findFirst();

        existing.ifPresentOrElse(
                x -> x.setSeasonality(seasonality),
                () -> {
                    SpotFaunaEmbedded emb = new SpotFaunaEmbedded();
                    emb.setFishId(fishObjId);
                    emb.setSeasonality(seasonality);
                    spot.getFauna().add(emb);
                }
        );

        spot.setFauna(sortFaunaByFishName(spot.getFauna()));
        fishingSpotMongoRepository.save(spot);
    }

    public void deleteFauna(String spotId, String fishId) {
        FishingSpotDocument spot = fishingSpotMongoRepository.findById(new ObjectId(spotId))
                .orElseThrow(() -> new RuntimeException("Место ловли не найдено"));

        ObjectId fishObjId = new ObjectId(fishId);

        spot.getFauna().removeIf(fa -> Objects.equals(fa.getFishId(), fishObjId));
        fishingSpotMongoRepository.save(spot);
    }

    public void deleteById(String spotId) {
        fishingSpotMongoRepository.deleteById(new ObjectId(spotId));
    }

    private List<SpotFaunaEmbedded> sortFaunaByFishName(List<SpotFaunaEmbedded> fauna) {
        if (fauna == null) return List.of();

        Map<ObjectId, String> fishNameById = fishTypeMongoRepository.findAll().stream()
                .collect(Collectors.toMap(FishTypeDocument::getFishId, FishTypeDocument::getFishName));

        return fauna.stream()
                .sorted(Comparator.comparing(
                        fa -> fishNameById.getOrDefault(fa.getFishId(), ""),
                        String.CASE_INSENSITIVE_ORDER
                ))
                .toList();
    }
}
