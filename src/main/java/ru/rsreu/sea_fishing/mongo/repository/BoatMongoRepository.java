package ru.rsreu.sea_fishing.mongo.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import ru.rsreu.sea_fishing.mongo.document.BoatDocument;

public interface BoatMongoRepository extends MongoRepository<BoatDocument, ObjectId> {
}
