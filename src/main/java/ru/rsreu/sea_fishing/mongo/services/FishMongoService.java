package ru.rsreu.sea_fishing.mongo.services;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.rsreu.sea_fishing.dto.FishFormDto;
import ru.rsreu.sea_fishing.mongo.document.FishTypeDocument;
import ru.rsreu.sea_fishing.mongo.repository.FishTypeMongoRepository;

import java.util.List;

@Profile("mongo")
@Service
@RequiredArgsConstructor
public class FishMongoService {

    private final FishTypeMongoRepository fishTypeMongoRepository;

    public List<FishTypeDocument> findAll() {
        return fishTypeMongoRepository.findAllByOrderByFishNameAsc();
    }

    public void save(FishFormDto dto) {
        FishTypeDocument doc = new FishTypeDocument();

        if (dto.fishId() != null && !dto.fishId().isBlank()) {
            doc.setFishId(new ObjectId(dto.fishId()));
        }
        doc.setFishName(dto.fishName());
        doc.setDifficultyLevel(dto.difficultyLevel());

        fishTypeMongoRepository.save(doc);
    }

    public void deleteById(String fishId) {
        fishTypeMongoRepository.deleteById(new ObjectId(fishId));
    }
}
