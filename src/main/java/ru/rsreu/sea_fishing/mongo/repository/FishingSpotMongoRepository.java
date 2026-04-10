package ru.rsreu.sea_fishing.mongo.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import ru.rsreu.sea_fishing.mongo.document.FishingSpotDocument;

public interface FishingSpotMongoRepository extends MongoRepository<FishingSpotDocument, ObjectId> {
}
