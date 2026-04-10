package ru.rsreu.sea_fishing.mongo.document;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "bookings")
public class BookingDocument {

    @Id
    private ObjectId bookingId;

    private ObjectId userId;

    private ObjectId excursionId;

    private ObjectId spotId;
}
