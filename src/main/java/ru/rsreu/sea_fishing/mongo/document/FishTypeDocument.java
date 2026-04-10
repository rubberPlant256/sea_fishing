package ru.rsreu.sea_fishing.mongo.document;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "fish_types")
public class FishTypeDocument {

    @Id
    private ObjectId fishId;

    private String fishName;

    private String difficultyLevel;
}
