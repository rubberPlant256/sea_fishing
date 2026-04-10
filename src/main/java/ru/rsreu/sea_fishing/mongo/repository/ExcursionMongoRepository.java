package ru.rsreu.sea_fishing.mongo.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import ru.rsreu.sea_fishing.mongo.document.ExcursionDocument;

import java.time.LocalDateTime;
import java.util.List;

public interface ExcursionMongoRepository extends MongoRepository<ExcursionDocument, ObjectId> {

    List<ExcursionDocument> findByStartTimeAfterOrderByStartTimeAsc(LocalDateTime startTime);
}
