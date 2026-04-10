package ru.rsreu.sea_fishing.mongo.document;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@Document(collection = "boats")
public class BoatDocument {

    @Id
    private ObjectId boatId;

    private String boatName;

    private Integer capacity;

    private BigDecimal pricePerHour;
}
