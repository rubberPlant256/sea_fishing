package ru.rsreu.sea_fishing.mongo.document;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "excursions")
public class ExcursionDocument {

//    @Id
//    private ObjectId id; // можно оставить id или переименовать в excursionId (ниже сделаю как вы просили)

    // если строго хотите имя excursionId:
     @Id private ObjectId excursionId;

    private ObjectId boatId;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
