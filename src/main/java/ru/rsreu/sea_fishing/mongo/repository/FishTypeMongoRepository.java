package ru.rsreu.sea_fishing.mongo.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import ru.rsreu.sea_fishing.mongo.document.FishTypeDocument;

import java.util.List;

public interface FishTypeMongoRepository extends MongoRepository<FishTypeDocument, ObjectId> {

    List<FishTypeDocument> findAllByOrderByFishNameAsc();
}
