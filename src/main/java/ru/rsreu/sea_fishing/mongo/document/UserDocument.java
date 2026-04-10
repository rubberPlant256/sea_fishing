package ru.rsreu.sea_fishing.mongo.document;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "users")
public class UserDocument {

    @Id
    private ObjectId userId;

    private String email;

    private String passwordHash;

    private String role;

    private String surname;
    private String name;
    private String patronymic;
}
