package ru.rsreu.sea_fishing.mongo.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import ru.rsreu.sea_fishing.mongo.document.BookingDocument;

import java.util.List;

public interface BookingMongoRepository extends MongoRepository<BookingDocument, ObjectId> {

    List<BookingDocument> findAllByUserId(ObjectId userId);

}
