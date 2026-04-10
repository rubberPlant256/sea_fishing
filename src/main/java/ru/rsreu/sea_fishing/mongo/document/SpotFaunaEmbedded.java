package ru.rsreu.sea_fishing.mongo.document;

import lombok.Data;
import org.bson.types.ObjectId;

@Data
public class SpotFaunaEmbedded {

    private ObjectId fishId;

    private String seasonality;
}
