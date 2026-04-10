package ru.rsreu.sea_fishing.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "app.mongo")
public class MongoProperties {
    private String host = "localhost";
    private int port = 27017;
    private String database = "sea_fishing";

    private String username;
    private String password;

    private String authSource = "admin";
}
