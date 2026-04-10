package ru.rsreu.sea_fishing.configs;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("mongo")
@RequiredArgsConstructor
public class MongoConfig {

    private final MongoProperties mongoProperties;

    @Bean
    public MongoClient mongoClient() {
        String userInfo = (mongoProperties.getUsername() != null && !mongoProperties.getUsername().isBlank())
                ? mongoProperties.getUsername() + ":" + mongoProperties.getPassword() + "@"
                : "";

        String uri = "mongodb://" + userInfo + mongoProperties.getHost() + ":" + mongoProperties.getPort() + "/"
                + mongoProperties.getDatabase()
                + (userInfo.isEmpty() ? "" : "?authSource=" + mongoProperties.getAuthSource());

        ConnectionString connectionString = new ConnectionString(uri);

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();

        return MongoClients.create(settings);
    }
}
