package ru.rsreu.sea_fishing.mongo.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import ru.rsreu.sea_fishing.mongo.document.UserDocument;

import java.util.Optional;

public interface UserMongoRepository extends MongoRepository<UserDocument, ObjectId> {

    Optional<UserDocument> findByEmail(String email);

}
