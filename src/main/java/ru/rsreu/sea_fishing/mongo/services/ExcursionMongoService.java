package ru.rsreu.sea_fishing.mongo.services;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.rsreu.sea_fishing.mongo.document.ExcursionDocument;
import ru.rsreu.sea_fishing.mongo.repository.ExcursionMongoRepository;

import java.time.LocalDateTime;
import java.util.List;

@Profile("mongo")
@Service
@RequiredArgsConstructor
public class ExcursionMongoService {

    private final ExcursionMongoRepository excursionMongoRepository;

    public List<ExcursionDocument> findAllFromDate() {
        LocalDateTime now = LocalDateTime.now();
        return excursionMongoRepository.findByStartTimeAfterOrderByStartTimeAsc(now);
    }

    public void deleteById(String id) {
        excursionMongoRepository.deleteById(new ObjectId(id));
    }

    public void save(ExcursionDocument doc) {
        excursionMongoRepository.save(doc);
    }
}
