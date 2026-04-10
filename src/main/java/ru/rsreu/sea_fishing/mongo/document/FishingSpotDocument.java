package ru.rsreu.sea_fishing.mongo.document;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "fishing_spots")
public class FishingSpotDocument {

    @Id
    private ObjectId spotId;

    private String spotName;

    private Integer depthMeters;

    private String bottomType;

    private List<SpotFaunaEmbedded> fauna = new ArrayList<>();
}
